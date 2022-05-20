package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.MeterValuesRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class MeterValuesRequestEvent(source: Any, sessionIndex: UUID, request: MeterValuesRequest) : ApplicationEvent(source)
