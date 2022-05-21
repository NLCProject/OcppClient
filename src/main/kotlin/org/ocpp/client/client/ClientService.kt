package org.ocpp.client.client

import eu.chargetime.ocpp.ClientEvents
import eu.chargetime.ocpp.JSONClient
import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile
import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request
import eu.chargetime.ocpp.model.core.*
import org.ocpp.client.client.interfaces.IClientInitService
import org.ocpp.client.client.interfaces.IClientService
import org.ocpp.client.event.client.ClientConnectedEvent
import org.ocpp.client.event.client.ClientConnectionLostEvent
import org.ocpp.client.event.client.request.*
import org.ocpp.client.utils.Ids
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch

@Service
class ClientService @Autowired constructor(
    private val applicationEventPublisher: ApplicationEventPublisher
) : IClientService, IClientInitService {

    private val port = 8887
    private var client: JSONClient? = null
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun init(ipAddress: String) {
        logger.info("Starting client on port '$port'")
        client = JSONClient(getCoreProfile(), Ids.getRandomIdString())
        client?.connect(
            "ws://$ipAddress:$port",
            object : ClientEvents {

                /**
                 *
                 */
                override fun connectionOpened() {
                    applicationEventPublisher.publishEvent(ClientConnectedEvent(source = this))
                    logger.info("New client session")
                }

                /**
                 *
                 */
                override fun connectionClosed() {
                    applicationEventPublisher.publishEvent(ClientConnectionLostEvent(source = this))
                    logger.info("Client session lost connection")
                }
            }
        )
    }

    override fun disconnect() {
        logger.info("Disconnecting client")
        client?.disconnect()
    }

    override fun send(request: Request): Confirmation {
        logger.info("Sending client request")
        val countDownLatch = CountDownLatch(1)
        var receivedConfirmation: Confirmation? = null

        client
            ?.send(request)
            ?.whenComplete { confirmation, ex ->
                logger.info("Client request sent | $confirmation")

                if (ex?.message != null)
                    logger.info("Error while sending client request: ${ex.message}")

                receivedConfirmation = confirmation
                countDownLatch.countDown()
            }

        countDownLatch.await()
        return receivedConfirmation ?: throw Exception("No confirmation received from server")
    }

    private fun getCoreProfile(): ClientCoreProfile = ClientCoreProfile(object : ClientCoreEventHandler {
        override fun handleChangeAvailabilityRequest(
            request: ChangeAvailabilityRequest
        ): ChangeAvailabilityConfirmation {
            logger.info("Received client request | Change Availability Request")
            val event = ChangeAvailabilityRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return ChangeAvailabilityConfirmation(AvailabilityStatus.Accepted)
        }

        override fun handleGetConfigurationRequest(
            request: GetConfigurationRequest
        ): GetConfigurationConfirmation {
            logger.info("Received client request | Get Configuration Request")
            val confirmation = GetConfigurationConfirmation()

            // TODO provide configuration
            val keyValueType = KeyValueType("TestKey", true)
            confirmation.configurationKey = arrayOf(keyValueType)
            confirmation.unknownKey = emptyArray()
            return confirmation
        }

        override fun handleChangeConfigurationRequest(
            request: ChangeConfigurationRequest
        ): ChangeConfigurationConfirmation {
            logger.info("Received client request | Change Configuration Request")
            val event = ChangeConfigurationRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return ChangeConfigurationConfirmation(ConfigurationStatus.Accepted)
        }

        override fun handleClearCacheRequest(request: ClearCacheRequest): ClearCacheConfirmation {
            logger.info("Received client request | Clear Cache Request")
            val event = ClearCacheRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return ClearCacheConfirmation(ClearCacheStatus.Accepted)
        }

        override fun handleDataTransferRequest(request: DataTransferRequest): DataTransferConfirmation {
            logger.info("Received client request | Data Transfer Request")
            val event = ClientDataTransferRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return DataTransferConfirmation(DataTransferStatus.Accepted)
        }

        override fun handleRemoteStartTransactionRequest(
            request: RemoteStartTransactionRequest
        ): RemoteStartTransactionConfirmation {
            logger.info("Received client request | Remote Start Transaction Request")
            val event = RemoteStartTransactionRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return RemoteStartTransactionConfirmation(RemoteStartStopStatus.Accepted)
        }

        override fun handleRemoteStopTransactionRequest(
            request: RemoteStopTransactionRequest
        ): RemoteStopTransactionConfirmation {
            logger.info("Received client request | Remote Stop Transaction Request")
            val event = RemoteStopTransactionRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return RemoteStopTransactionConfirmation(RemoteStartStopStatus.Accepted)
        }

        override fun handleResetRequest(request: ResetRequest): ResetConfirmation {
            logger.info("Received client request | Reset Request")
            val event = ResetRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return ResetConfirmation(ResetStatus.Accepted)
        }

        override fun handleUnlockConnectorRequest(request: UnlockConnectorRequest): UnlockConnectorConfirmation {
            logger.info("Received client request | Unlock Connector Request")
            val event = UnlockConnectorRequestEvent(request = request, source = this)
            applicationEventPublisher.publishEvent(event)
            return UnlockConnectorConfirmation(UnlockStatus.Unlocked)
        }
    })
}
