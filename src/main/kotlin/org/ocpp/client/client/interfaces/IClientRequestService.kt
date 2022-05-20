package org.ocpp.client.client.interfaces

import eu.chargetime.ocpp.model.core.*

/**
 *
 */
interface IClientRequestService {

    /**
     *
     */
    fun authorize(idTag: String): AuthorizeConfirmation

    /**
     *
     */
    fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation

    /**
     *
     */
    fun heartbeat(): HeartbeatConfirmation

    /**
     *
     */
    fun startTransaction(connectorId: Int, idTag: String, meterStart: Int): StartTransactionConfirmation

    /**
     *
     */
    fun stopTransaction(meterStop: Int, transactionId: Int): StopTransactionConfirmation

    /**
     *
     */
    fun bootNotification(chargePointVendor: String, chargePointModel: String): BootNotificationConfirmation

    /**
     *
     */
    fun meterValues(connectorId: Int, meterValue: Array<MeterValue>, transactionId: Int): MeterValuesConfirmation

    /**
     *
     */
    fun statusNotification(
        connectorId: Int,
        errorCode: ChargePointErrorCode,
        status: ChargePointStatus
    ): StatusNotificationConfirmation
}
