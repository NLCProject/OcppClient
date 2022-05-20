package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.AuthorizeRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class AuthorizeRequestEvent(source: Any, sessionIndex: UUID, request: AuthorizeRequest) : ApplicationEvent(source)
