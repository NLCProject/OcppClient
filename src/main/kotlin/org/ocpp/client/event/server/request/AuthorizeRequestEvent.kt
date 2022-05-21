package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.AuthorizeRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming authorize request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class AuthorizeRequestEvent(source: Any, val sessionIndex: UUID, val request: AuthorizeRequest) :
    ApplicationEvent(source)
