package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StopTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming stop transaction request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class StopTransactionRequestEvent(source: Any, sessionIndex: UUID, request: StopTransactionRequest) :
    ApplicationEvent(source)
