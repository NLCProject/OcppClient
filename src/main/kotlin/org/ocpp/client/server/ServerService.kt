package org.ocpp.client.server

import eu.chargetime.ocpp.JSONServer
import eu.chargetime.ocpp.ServerEvents
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile
import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request
import eu.chargetime.ocpp.model.SessionInformation
import eu.chargetime.ocpp.model.core.*
import org.ocpp.client.configuration.Configuration
import org.ocpp.client.event.server.ClientConnectedOnServerEvent
import org.ocpp.client.event.server.ClientSessionLostEvent
import org.ocpp.client.event.server.request.*
import org.ocpp.client.server.interfaces.IServerInitService
import org.ocpp.client.server.interfaces.IServerService
import org.ocpp.client.utils.DateTimeUtil
import org.ocpp.client.utils.Heartbeat
import org.ocpp.client.utils.Ids
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.CountDownLatch

@Service
class ServerService @Autowired constructor(
    private val applicationEventPublisher: ApplicationEventPublisher
) : IServerService, IServerInitService {

    private var server: JSONServer? = null
    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        var testMode = false
        var initialSessionIndex: UUID? = null
    }

    override fun init(ipAddress: String, testMode: Boolean) {
        logger.info("Starting server on IP address '$ipAddress:${Configuration.port}' | Test mode '$testMode'")
        if (testMode)
            ServerService.testMode = true

        server = JSONServer(getCoreProfile())
        server?.open(
            ipAddress,
            Configuration.port,
            object : ServerEvents {

                /**
                 *
                 */
                override fun newSession(sessionIndex: UUID, information: SessionInformation) {
                    logger.info(
                        "New client session '$sessionIndex' | Identifier '${information.identifier}' " +
                            "| IP address '${information.address.hostName}'"
                    )

                    val event = ClientConnectedOnServerEvent(
                        source = this,
                        sessionIndex = sessionIndex,
                        information = information
                    )

                    initialSessionIndex = sessionIndex
                    applicationEventPublisher.publishEvent(event)
                }

                /**
                 *
                 */
                override fun lostSession(sessionIndex: UUID) {
                    logger.info("Client session '$sessionIndex' lost connection")
                    val event = ClientSessionLostEvent(source = this, sessionIndex = sessionIndex)
                    applicationEventPublisher.publishEvent(event)
                }
            }
        )
    }

    override fun close() {
        logger.info("Shutting down server")
        server?.close()
    }

    override fun send(request: Request, sessionIndex: String): Confirmation {
        logger.info("Sending server -> client request | session index '$sessionIndex' | Test mode '$testMode'")
        val countDownLatch = CountDownLatch(1)
        var receivedConfirmation: Confirmation? = null
        val validSessionIndex = if (testMode) initialSessionIndex else UUID.fromString(sessionIndex)

        server
            ?.send(validSessionIndex, request)
            ?.whenComplete { confirmation, ex ->
                logger.info("Server request sent | $confirmation | session index '$sessionIndex'")

                if (ex?.message != null)
                    logger.info("Error while sending request | session index '$sessionIndex' | ${ex.message}")

                receivedConfirmation = confirmation
                countDownLatch.countDown()
            }

        countDownLatch.await()
        return receivedConfirmation ?: throw Exception("No confirmation received from client | " +
            "session index '$sessionIndex'")
    }

    private fun getCoreProfile(): ServerCoreProfile = ServerCoreProfile(object : ServerCoreEventHandler {

        override fun handleAuthorizeRequest(sessionIndex: UUID, request: AuthorizeRequest): AuthorizeConfirmation {
            logger.info("Received server request | Authorization Request | Session Index '$sessionIndex'")
            val event = AuthorizeRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return AuthorizeConfirmation(createIdTagInfo())
        }

        override fun handleDataTransferRequest(
            sessionIndex: UUID,
            request: DataTransferRequest
        ): DataTransferConfirmation {
            logger.info("Received server request | Data Transfer Request | Session Index '$sessionIndex'")
            val event = ServerDataTransferRequestEvent(
                sessionIndex = sessionIndex,
                request = request,
                source = this
            )

            applicationEventPublisher.publishEvent(event)
            return DataTransferConfirmation(DataTransferStatus.Accepted)
        }

        override fun handleHeartbeatRequest(sessionIndex: UUID, request: HeartbeatRequest): HeartbeatConfirmation {
            logger.info("Received server request | Heartbeat Request | Session Index '$sessionIndex'")
            val event = HeartbeatRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return HeartbeatConfirmation(DateTimeUtil.dateNow())
        }

        override fun handleStartTransactionRequest(
            sessionIndex: UUID,
            request: StartTransactionRequest
        ): StartTransactionConfirmation {
            logger.info("Received server request | Start Transaction Request | Session Index '$sessionIndex'")
            val transactionId = Ids.getRandomId()
            val event = StartTransactionRequestEvent(
                transactionId = transactionId,
                sessionIndex = sessionIndex,
                request = request,
                source = this
            )

            applicationEventPublisher.publishEvent(event)
            return StartTransactionConfirmation(createIdTagInfo(), transactionId)
        }

        override fun handleStopTransactionRequest(
            sessionIndex: UUID,
            request: StopTransactionRequest
        ): StopTransactionConfirmation {
            logger.info("Received server request | Stop Transaction Request | Session Index '$sessionIndex'")
            val event = StopTransactionRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            val confirmation = StopTransactionConfirmation()
            confirmation.idTagInfo = createIdTagInfo()
            return confirmation
        }

        override fun handleBootNotificationRequest(
            sessionIndex: UUID,
            request: BootNotificationRequest
        ): BootNotificationConfirmation {
            logger.info("Received server request | Boot Notification Request | Session Index '$sessionIndex'")
            val event = BootNotificationRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return BootNotificationConfirmation(
                DateTimeUtil.dateNow(),
                Heartbeat.heartbeatInterval,
                RegistrationStatus.Accepted
            )
        }

        override fun handleMeterValuesRequest(
            sessionIndex: UUID,
            request: MeterValuesRequest
        ): MeterValuesConfirmation {
            logger.info("Received server request | Meter Values Request | Session Index '$sessionIndex'")
            val event = MeterValuesRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return MeterValuesConfirmation()
        }

        override fun handleStatusNotificationRequest(
            sessionIndex: UUID,
            request: StatusNotificationRequest
        ): StatusNotificationConfirmation {
            logger.info("Received server request | Status Notification Request | Session Index '$sessionIndex'")
            val event = StatusNotificationRequestEvent(
                sessionIndex = sessionIndex,
                request = request,
                source = this
            )

            applicationEventPublisher.publishEvent(event)
            return StatusNotificationConfirmation()
        }
    })

    private fun createIdTagInfo(): IdTagInfo {
        val idTagInfo = IdTagInfo(AuthorizationStatus.Accepted)
        idTagInfo.expiryDate = DateTimeUtil.dateNow().plusWeeks(1)
        idTagInfo.parentIdTag = String()
        return idTagInfo
    }
}
