package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.DataTransferRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class DataTransferRequestEvent(source: Any, request: DataTransferRequest) : ApplicationEvent(source)
