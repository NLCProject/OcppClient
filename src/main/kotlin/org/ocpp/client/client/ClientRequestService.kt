package org.ocpp.client.client

import eu.chargetime.ocpp.model.core.*
import org.ocpp.client.client.interfaces.IClientRequestService
import org.ocpp.client.client.interfaces.IClientService
import org.ocpp.client.utils.DateTimeUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ClientRequestService @Autowired constructor(
    private val service: IClientService
) : IClientRequestService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun authorize(idTag: String): AuthorizeConfirmation {
        logger.info("Sending client request | Authorize")
        val request = AuthorizeRequest(idTag)
        return service.send(request) as AuthorizeConfirmation
    }

    override fun dataTransfer(vendorId: String, data: String): DataTransferConfirmation {
        logger.info("Sending client request | Data Transfer")
        val request = DataTransferRequest(vendorId)
        request.data = data
        return service.send(request) as DataTransferConfirmation
    }

    override fun heartbeat(): HeartbeatConfirmation {
        logger.info("Sending client request | Heartbeat")
        val request = HeartbeatRequest()
        return service.send(request) as HeartbeatConfirmation
    }

    override fun startTransaction(connectorId: Int, idTag: String, meterStart: Int): StartTransactionConfirmation {
        logger.info("Sending client request | Start Transaction")
        val request = StartTransactionRequest(connectorId, idTag, meterStart, DateTimeUtil.dateNow())
        return service.send(request) as StartTransactionConfirmation
    }

    override fun stopTransaction(meterStop: Int, transactionId: Int): StopTransactionConfirmation {
        logger.info("Sending client request | Stop Transaction")
        val request = StopTransactionRequest(meterStop, DateTimeUtil.dateNow(), transactionId)
        return service.send(request) as StopTransactionConfirmation
    }

    override fun bootNotification(chargePointVendor: String, chargePointModel: String): BootNotificationConfirmation {
        logger.info("Sending client request | Boot Notification")
        val request = BootNotificationRequest(chargePointVendor, chargePointModel)
        return service.send(request) as BootNotificationConfirmation
    }

    override fun meterValues(
        connectorId: Int,
        meterValue: Array<MeterValue>,
        transactionId: Int
    ): MeterValuesConfirmation {
        logger.info("Sending client request | Meter Values")
        val request = MeterValuesRequest(connectorId)
        request.meterValue = meterValue
        request.transactionId = transactionId
        return service.send(request) as MeterValuesConfirmation
    }

    override fun statusNotification(
        connectorId: Int,
        errorCode: ChargePointErrorCode,
        status: ChargePointStatus
    ): StatusNotificationConfirmation {
        logger.info("Sending client request | Status Notification")
        val request = StatusNotificationRequest(connectorId, errorCode, status)
        return service.send(request) as StatusNotificationConfirmation
    }
}
