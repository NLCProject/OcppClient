package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming change availability request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class ChangeAvailabilityRequestEvent(source: Any, val request: ChangeAvailabilityRequest) : ApplicationEvent(source)
