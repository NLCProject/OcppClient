package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StatusNotificationRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class StatusNotificationRequestEvent(source: Any, sessionIndex: UUID, request: StatusNotificationRequest) :
    ApplicationEvent(source)
