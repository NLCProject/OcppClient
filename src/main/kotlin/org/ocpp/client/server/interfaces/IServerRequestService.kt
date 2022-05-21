package org.ocpp.client.server.interfaces

import eu.chargetime.ocpp.model.core.*

/**
 * Handles outgoing requests from server to client. Every request will wait for the response from the client and returns
 * an async confirmation.
 */
interface IServerRequestService {

    /**
     * Change availability of the charge point.
     *
     * @param connectorId .
     * @param type .
     * @return Async confirmation.
     */
    fun changeAvailability(connectorId: Int, type: AvailabilityType): ChangeAvailabilityConfirmation

    /**
     * Request configuration from charge point.
     *
     * @param keys Keys of configuration to request
     * @return Async confirmation.
     */
    fun getConfiguration(keys: Array<String>): GetConfigurationConfirmation

    /**
     * Change single configuration.
     *
     * @param key Configuration key to change. Max length is 50.
     * @param value New value to apply. Max length is 500.
     * @return Async confirmation.
     */
    fun changeConfiguration(key: String, value: String): ChangeConfigurationConfirmation

    /**
     * Clear cache.
     *
     * @return Async confirmation.
     */
    fun clearCache(): ClearCacheConfirmation

    /**
     * Send data to client.
     *
     * @param vendorId .
     * @param data Custom data as string
     * @return Async confirmation.
     */
    fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation

    /**
     * Start transaction at charge point.
     *
     * @param connectorId .
     * @param idTag Max length is 20.
     * @param profile Charging profile to apply
     * @return Async confirmation.
     */
    fun remoteStartTransaction(
        connectorId: Int,
        idTag: String,
        profile: ChargingProfile
    ): RemoteStartTransactionConfirmation

    /**
     * Stop transaction at charge point.
     *
     * @param transactionId .
     * @return Async confirmation.
     */
    fun remoteStopTransaction(transactionId: Int): RemoteStopTransactionConfirmation

    /**
     * Reset charge point.
     *
     * @param type .
     * @return Async confirmation.
     */
    fun reset(type: ResetType): ResetConfirmation

    /**
     * Unlock connector of charge point.
     *
     * @param connectorId .
     * @return Async confirmation.
     */
    fun unlockConnector(connectorId: Int): UnlockConnectorConfirmation
}
