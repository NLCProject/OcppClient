package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming remote start transaction request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class RemoteStartTransactionRequestEvent(source: Any, val request: RemoteStartTransactionRequest) :
    ApplicationEvent(source)
