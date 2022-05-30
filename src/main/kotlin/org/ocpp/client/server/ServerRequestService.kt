package org.ocpp.client.server

import eu.chargetime.ocpp.model.core.*
import org.ocpp.client.server.interfaces.IServerRequestService
import org.ocpp.client.server.interfaces.IServerService
import org.ocpp.client.utils.Ids
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ServerRequestService @Autowired constructor(
    private val service: IServerService
) : IServerRequestService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun changeAvailability(connectorId: Int, type: AvailabilityType): ChangeAvailabilityConfirmation {
        logger.info("Sending server request | Change Availability")
        val request = ChangeAvailabilityRequest(connectorId, type)
        return service.send(request = request) as ChangeAvailabilityConfirmation
    }

    override fun getConfiguration(keys: Array<String>): GetConfigurationConfirmation {
        // Currently, not handled by server
        logger.info("Sending server request | Get Configuration")
        val request = GetConfigurationRequest()
        request.key = keys
        return service.send(request = request) as GetConfigurationConfirmation
    }

    override fun changeConfiguration(key: String, value: String): ChangeConfigurationConfirmation {
        // Currently, not handled by server
        logger.info("Sending server request | Change Configuration")
        val request = ChangeConfigurationRequest(key, value)
        return service.send(request = request) as ChangeConfigurationConfirmation
    }

    override fun clearCache(): ClearCacheConfirmation {
        logger.info("Sending server request | Clear Cache")
        val request = ClearCacheRequest()
        return service.send(request = request) as ClearCacheConfirmation
    }

    override fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation {
        // Currently, not handled by server
        logger.info("Sending server request | Data Transfer")
        val request = DataTransferRequest(vendorId)
        request.data = data
        return service.send(request = request) as DataTransferConfirmation
    }

    override fun remoteStartTransaction(
        connectorId: Int,
        idTag: String,
        profile: ChargingProfile?
    ): RemoteStartTransactionConfirmation {
        logger.info("Sending server request | Remote Start Transaction")
        val request = RemoteStartTransactionRequest(Ids.getRandomId().toString())
        request.idTag = idTag
        request.connectorId = connectorId
        request.chargingProfile = profile
        return service.send(request = request) as RemoteStartTransactionConfirmation
    }

    override fun remoteStopTransaction(transactionId: Int): RemoteStopTransactionConfirmation {
        logger.info("Sending server request | Remote Stop Transaction")
        val request = RemoteStopTransactionRequest(transactionId)
        return service.send(request = request) as RemoteStopTransactionConfirmation
    }

    override fun reset(type: ResetType): ResetConfirmation {
        logger.info("Sending server request | Reset")
        val request = ResetRequest(type)
        return service.send(request = request) as ResetConfirmation
    }

    override fun unlockConnector(connectorId: Int): UnlockConnectorConfirmation {
        logger.info("Sending server request | Unlock Connector")
        val request = UnlockConnectorRequest(connectorId)
        return service.send(request = request) as UnlockConnectorConfirmation
    }
}
