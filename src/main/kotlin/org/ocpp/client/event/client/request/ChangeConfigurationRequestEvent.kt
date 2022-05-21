package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.*
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming change configuration request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class ChangeConfigurationRequestEvent(source: Any, request: ChangeConfigurationRequest) : ApplicationEvent(source)
