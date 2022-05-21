package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.ResetRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming reset request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class ResetRequestEvent(source: Any, val request: ResetRequest) : ApplicationEvent(source)
