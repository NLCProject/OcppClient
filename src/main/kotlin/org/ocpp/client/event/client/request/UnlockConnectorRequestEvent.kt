package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.UnlockConnectorRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming unlock connector request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class UnlockConnectorRequestEvent(source: Any, request: UnlockConnectorRequest) : ApplicationEvent(source)
