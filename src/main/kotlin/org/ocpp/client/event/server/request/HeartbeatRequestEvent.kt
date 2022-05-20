package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.HeartbeatRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class HeartbeatRequestEvent(source: Any, sessionIndex: UUID, request: HeartbeatRequest) : ApplicationEvent(source)
