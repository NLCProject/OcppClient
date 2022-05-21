package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.RemoteStopTransactionRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming remote stop transaction request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class RemoteStopTransactionRequestEvent(source: Any, val request: RemoteStopTransactionRequest) :
    ApplicationEvent(source)
