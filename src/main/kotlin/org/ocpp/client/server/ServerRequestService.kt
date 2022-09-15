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

    override fun changeAvailability(
        connectorId: Int,
        type: AvailabilityType,
        sessionIndex: String
    ): ChangeAvailabilityConfirmation {
        logger.info("Sending server request | Change Availability | session index '$sessionIndex'")
        val request = ChangeAvailabilityRequest(connectorId, type)
        return service.send(request = request, sessionIndex = sessionIndex) as ChangeAvailabilityConfirmation
    }

    override fun getConfiguration(keys: Array<String>, sessionIndex: String): GetConfigurationConfirmation {
        // Currently, not handled by server
        logger.info("Sending server request | Get Configuration | session index '$sessionIndex'")
        val request = GetConfigurationRequest()
        request.key = keys
        return service.send(request = request, sessionIndex = sessionIndex) as GetConfigurationConfirmation
    }

    override fun changeConfiguration(
        key: String,
        value: String,
        sessionIndex: String
    ): ChangeConfigurationConfirmation {
        // Currently, not handled by server
        logger.info("Sending server request | Change Configuration | session index '$sessionIndex'")
        val request = ChangeConfigurationRequest(key, value)
        return service.send(request = request, sessionIndex = sessionIndex) as ChangeConfigurationConfirmation
    }

    override fun clearCache(sessionIndex: String): ClearCacheConfirmation {
        logger.info("Sending server request | Clear Cache | session index '$sessionIndex'")
        val request = ClearCacheRequest()
        return service.send(request = request, sessionIndex = sessionIndex) as ClearCacheConfirmation
    }

    override fun dataTransfer(vendorId: String, data: String, sessionIndex: String): DataTransferConfirmation {
        // Currently, not handled by server
        logger.info("Sending server request | Data Transfer | session index '$sessionIndex'")
        val request = DataTransferRequest(vendorId)
        request.data = data
        return service.send(request = request, sessionIndex = sessionIndex) as DataTransferConfirmation
    }

    override fun remoteStartTransaction(
        connectorId: Int,
        idTag: String,
        profile: ChargingProfile?,
        sessionIndex: String
    ): RemoteStartTransactionConfirmation {
        logger.info("Sending server request | Remote Start Transaction | session index '$sessionIndex'")
        val request = RemoteStartTransactionRequest(Ids.getRandomId().toString())
        request.idTag = idTag
        request.connectorId = connectorId
        request.chargingProfile = profile
        return service.send(request = request, sessionIndex = sessionIndex) as RemoteStartTransactionConfirmation
    }

    override fun remoteStopTransaction(transactionId: Int, sessionIndex: String): RemoteStopTransactionConfirmation {
        logger.info("Sending server request | Remote Stop Transaction | session index '$sessionIndex'")
        val request = RemoteStopTransactionRequest(transactionId)
        return service.send(request = request, sessionIndex = sessionIndex) as RemoteStopTransactionConfirmation
    }

    override fun reset(type: ResetType, sessionIndex: String): ResetConfirmation {
        logger.info("Sending server request | Reset")
        val request = ResetRequest(type)
        return service.send(request = request, sessionIndex = sessionIndex) as ResetConfirmation
    }

    override fun unlockConnector(connectorId: Int, sessionIndex: String): UnlockConnectorConfirmation {
        logger.info("Sending server request | Unlock Connector | session index '$sessionIndex'")
        val request = UnlockConnectorRequest(connectorId)
        return service.send(request = request, sessionIndex = sessionIndex) as UnlockConnectorConfirmation
    }
}
