package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.DataTransferRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class DataTransferRequestEvent(source: Any, sessionIndex: UUID, request: DataTransferRequest) : ApplicationEvent(source)
