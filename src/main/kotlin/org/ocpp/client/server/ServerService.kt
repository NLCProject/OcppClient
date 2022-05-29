package org.ocpp.client.server

import eu.chargetime.ocpp.JSONServer
import eu.chargetime.ocpp.ServerEvents
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile
import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request
import eu.chargetime.ocpp.model.SessionInformation
import eu.chargetime.ocpp.model.core.*
import org.ocpp.client.event.server.ClientConnectedEvent
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

    private val port = 8887
    private var server: JSONServer? = null
    private val logger = LoggerFactory.getLogger(this::class.java)
    private var sessionIndex: UUID? = null

    fun setSessionIndex(sessionIndex: UUID?) {
        this.sessionIndex = sessionIndex
    }

    override fun init(ipAddress: String) {
        logger.info("Starting server on IP address '$ipAddress' and port '$port'")
        server = JSONServer(getCoreProfile())
        server?.open(
            ipAddress, port,
            object : ServerEvents {

                /**
                 *
                 */
                override fun newSession(sessionIndex: UUID, information: SessionInformation) {
                    logger.info(
                        "New client session '$sessionIndex' | Identifier '${information.identifier}' " +
                            "| IP address '${information.address.hostName}'"
                    )

                    setSessionIndex(sessionIndex = sessionIndex)
                    val event = ClientConnectedEvent(
                        source = this,
                        sessionIndex = sessionIndex,
                        information = information
                    )

                    applicationEventPublisher.publishEvent(event)
                }

                /**
                 *
                 */
                override fun lostSession(sessionIndex: UUID) {
                    logger.info("Client session '$sessionIndex' lost connection")
                    setSessionIndex(sessionIndex = null)
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

    override fun send(request: Request): Confirmation {
        logger.info("Sending server -> client request")
        if (sessionIndex == null)
            throw Exception("No session index found")

        val countDownLatch = CountDownLatch(1)
        var receivedConfirmation: Confirmation? = null

        server
            ?.send(sessionIndex, request)
            ?.whenComplete { confirmation, ex ->
                logger.info("Server request sent | $confirmation")

                if (ex?.message != null)
                    logger.info("Error while sending request: ${ex.message}")

                receivedConfirmation = confirmation
                countDownLatch.countDown()
            }

        countDownLatch.await()
        return receivedConfirmation ?: throw Exception("No confirmation received from client")
    }

    private fun getCoreProfile(): ServerCoreProfile =
        ServerCoreProfile(object : ServerCoreEventHandler {

            override fun handleAuthorizeRequest(sessionIndex: UUID, request: AuthorizeRequest): AuthorizeConfirmation {
                logger.info("Received server request | Authorization Request | Session Index '$sessionIndex'")
                val event = AuthorizeRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
                applicationEventPublisher.publishEvent(event)
                return AuthorizeConfirmation(IdTagInfo(AuthorizationStatus.Accepted))
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
                return StartTransactionConfirmation(IdTagInfo(AuthorizationStatus.Accepted), transactionId)
            }

            override fun handleStopTransactionRequest(
                sessionIndex: UUID,
                request: StopTransactionRequest
            ): StopTransactionConfirmation {
                logger.info("Received server request | Stop Transaction Request | Session Index '$sessionIndex'")
                val event = StopTransactionRequestEvent(sessionIndex = sessionIndex, request = request, source = this)
                applicationEventPublisher.publishEvent(event)
                return StopTransactionConfirmation()
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
}
