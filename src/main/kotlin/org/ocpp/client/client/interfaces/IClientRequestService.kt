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
     * @param idTag Max size is 20. Authorization ID is free to choose.
     * @return Async confirmation.
     */
    fun authorize(idTag: String): AuthorizeConfirmation

    /**
     * Sending data to the server.
     *
     * @param vendorId This identifies the Vendor specific implementation.
     * @param data Custom data as string.
     * @return Async confirmation.
     */
    fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation

    /**
     * Keep-alive heartbeat. Must be sent in a specific interval. Generally, the heartbeat interval is received by the
     * server via the BootNotificationRequest from the server.
     * @return Async confirmation.
     */
    fun heartbeat(): HeartbeatConfirmation

    /**
     * Start transaction.
     *
     * @param connectorId .
     * @param idTag Max size is 20.
     * @param meterStart This contains the meter value in Wh for the connector at start of the transaction.
     * @param reservationId .
     * @return Async confirmation.
     */
    fun startTransaction(
        connectorId: Int,
        idTag: String,
        meterStart: Int,
        reservationId: Int = 0
    ): StartTransactionConfirmation

    /**
     * Stop transaction.
     *
     * @param meterStop This contains the meter value in Wh for the connector at end of the transaction.
     * @param transactionData Array of meter values.
     * @param transactionId Transaction to stop.
     * @param reason Reason to stop.
     * @return Async confirmation.
     */
    fun stopTransaction(
        meterStop: Int,
        transactionData: Array<MeterValue> = emptyArray(),
        transactionId: Int,
        reason: Reason = Reason.Local
    ): StopTransactionConfirmation

    /**
     * Notify server about a (re)boot.
     *
     * @param chargePointVendor This contains a value that identifies the vendor of the ChargePoint.
     * @param chargePointModel Max size is 20. This contains a value that identifies the model of the ChargePoint.
     * @return Async confirmation.
     */
    fun bootNotification(chargePointVendor: String, chargePointModel: String): BootNotificationConfirmation

    /**
     * Sends charging meter values during an ongoing transactions. The current charging value is sent in Wh.
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
     * @param information Additional information.
     * @param vendorId .
     * @param vendorErrorCode .
     * @return Async confirmation.
     */
    fun statusNotification(
        connectorId: Int,
        errorCode: ChargePointErrorCode,
        status: ChargePointStatus,
        information: String = String(),
        vendorId: String = String(),
        vendorErrorCode: String = String(),
    ): StatusNotificationConfirmation
}
