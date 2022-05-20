package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.ResetRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class ResetRequestEvent(source: Any, request: ResetRequest) : ApplicationEvent(source)
