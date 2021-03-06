package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StatusNotificationRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming status notification request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class StatusNotificationRequestEvent(source: Any, val sessionIndex: UUID, val request: StatusNotificationRequest) :
    ApplicationEvent(source)
