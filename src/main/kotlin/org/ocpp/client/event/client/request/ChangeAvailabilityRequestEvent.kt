package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class ChangeAvailabilityRequestEvent(source: Any, request: ChangeAvailabilityRequest) : ApplicationEvent(source)
