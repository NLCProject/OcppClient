package org.ocpp.client.server.interfaces

import eu.chargetime.ocpp.model.core.*

/**
 *
 */
interface IServerRequestService {

    /**
     *
     */
    fun changeAvailability(connectorId: Int, type: AvailabilityType): ChangeAvailabilityConfirmation

    /**
     *
     */
    fun getConfiguration(keys: Array<String>): GetConfigurationConfirmation

    /**
     *
     */
    fun changeConfiguration(key: String, value: String): ChangeConfigurationConfirmation

    /**
     *
     */
    fun clearCache(): ClearCacheConfirmation

    /**
     *
     */
    fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation

    /**
     *
     */
    fun remoteStartTransaction(
        connectorId: Int,
        idTag: String,
        profile: ChargingProfile
    ): RemoteStartTransactionConfirmation

    /**
     *
     */
    fun remoteStopTransaction(transactionId: Int): RemoteStopTransactionConfirmation

    /**
     *
     */
    fun reset(type: ResetType): ResetConfirmation

    /**
     *
     */
    fun unlockConnector(connectorId: Int): UnlockConnectorConfirmation
}
