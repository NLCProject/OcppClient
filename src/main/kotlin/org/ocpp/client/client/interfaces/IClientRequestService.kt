package org.ocpp.client.client.interfaces

import eu.chargetime.ocpp.model.core.*

/**
 * Handles outgoing requests from client to server. Every request will wait for the response from the server and returns
 * an async confirmation.
 */
interface IClientRequestService {

    /**
     * Authorize charge point at server.
     *
     * @param idTag .
     * @return Async confirmation.
     */
    fun authorize(idTag: String): AuthorizeConfirmation

    /**
     * Sending data to the server.
     *
     * @param vendorId .
     * @param data Custom data as string.
     * @return Async confirmation.
     */
    fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation

    /**
     * Keep-alive heartbeat. Must be send in a specific interval. Generally, the heartbeat interval is received by the
     * server via the BootNotificationRequest from the server.
     * @return Async confirmation.
     */
    fun heartbeat(): HeartbeatConfirmation

    /**
     * Start transaction.
     *
     * @param connectorId .
     * @param idTag .
     * @param meterStart .
     * @return Async confirmation.
     */
    fun startTransaction(connectorId: Int, idTag: String, meterStart: Int): StartTransactionConfirmation

    /**
     * Stop transaction.
     *
     * @param meterStop .
     * @param transactionId .
     * @return Async confirmation.
     */
    fun stopTransaction(meterStop: Int, transactionId: Int): StopTransactionConfirmation

    /**
     * Notify server about a (re)boot.
     *
     * @param chargePointVendor .
     * @param chargePointModel .
     * @return Async confirmation.
     */
    fun bootNotification(chargePointVendor: String, chargePointModel: String): BootNotificationConfirmation

    /**
     * Sends sample values for diagnostic.
     *
     * @param connectorId .
     * @param meterValue Array of meter values.
     * @param transactionId .
     * @return Async confirmation.
     */
    fun meterValues(connectorId: Int, meterValue: Array<MeterValue>, transactionId: Int): MeterValuesConfirmation

    /**
     * Send status and/or error.
     *
     * @param connectorId .
     * @param errorCode .
     * @param status .
     * @return Async confirmation.
     */
    fun statusNotification(
        connectorId: Int,
        errorCode: ChargePointErrorCode,
        status: ChargePointStatus
    ): StatusNotificationConfirmation
}
