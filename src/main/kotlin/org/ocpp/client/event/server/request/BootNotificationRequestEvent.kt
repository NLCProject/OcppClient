package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.BootNotificationRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class BootNotificationRequestEvent(source: Any, sessionIndex: UUID, request: BootNotificationRequest) :
    ApplicationEvent(source)
