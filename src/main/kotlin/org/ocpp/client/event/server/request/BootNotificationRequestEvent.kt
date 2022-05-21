package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.BootNotificationRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming boot notification request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class BootNotificationRequestEvent(source: Any, val sessionIndex: UUID, val request: BootNotificationRequest) :
    ApplicationEvent(source)
