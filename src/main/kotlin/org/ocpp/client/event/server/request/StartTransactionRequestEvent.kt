package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StartTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming start transaction heartbeat request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class StartTransactionRequestEvent(source: Any, sessionIndex: UUID, request: StartTransactionRequest) :
    ApplicationEvent(source)
