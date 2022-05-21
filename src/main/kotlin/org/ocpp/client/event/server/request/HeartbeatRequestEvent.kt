package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.HeartbeatRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming heartbeat request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class HeartbeatRequestEvent(source: Any, val sessionIndex: UUID, val request: HeartbeatRequest) :
    ApplicationEvent(source)
