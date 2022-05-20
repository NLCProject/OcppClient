package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StopTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class StopTransactionRequestEvent(source: Any, sessionIndex: UUID, request: StopTransactionRequest) :
    ApplicationEvent(source)
