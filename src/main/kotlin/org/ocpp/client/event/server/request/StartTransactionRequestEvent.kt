package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StartTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class StartTransactionRequestEvent(source: Any, sessionIndex: UUID, request: StartTransactionRequest) :
    ApplicationEvent(source)
