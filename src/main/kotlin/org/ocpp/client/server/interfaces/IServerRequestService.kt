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
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun changeAvailability(
        connectorId: Int,
        type: AvailabilityType,
        sessionIndex: String
    ): ChangeAvailabilityConfirmation

    /**
     * Request configuration from charge point.
     *
     * @param keys Keys of configuration to request.
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun getConfiguration(keys: Array<String>, sessionIndex: String): GetConfigurationConfirmation

    /**
     * Change single configuration.
     *
     * @param key Configuration key to change. Max length is 50.
     * @param value New value to apply. Max length is 500.
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun changeConfiguration(key: String, value: String, sessionIndex: String): ChangeConfigurationConfirmation

    /**
     * Clear cache.
     *
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun clearCache(sessionIndex: String): ClearCacheConfirmation

    /**
     * Send data to client.
     *
     * @param vendorId .
     * @param data Custom data as string.
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun dataTransfer(vendorId: String, data: String, sessionIndex: String): DataTransferConfirmation

    /**
     * Start transaction at charge point.
     *
     * @param connectorId .
     * @param idTag Max length is 20.
     * @param profile Charging profile to apply.
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun remoteStartTransaction(
        connectorId: Int,
        idTag: String,
        profile: ChargingProfile?,
        sessionIndex: String
    ): RemoteStartTransactionConfirmation

    /**
     * Stop transaction at charge point.
     *
     * @param transactionId .
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun remoteStopTransaction(transactionId: Int, sessionIndex: String): RemoteStopTransactionConfirmation

    /**
     * Reset charge point.
     *
     * @param type .
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun reset(type: ResetType, sessionIndex: String): ResetConfirmation

    /**
     * Unlock connector of charge point.
     *
     * @param connectorId .
     * @param sessionIndex Session index of client.
     * @return Async confirmation.
     */
    fun unlockConnector(connectorId: Int, sessionIndex: String): UnlockConnectorConfirmation
}
