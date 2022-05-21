package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.MeterValuesRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming meter values request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class MeterValuesRequestEvent(source: Any, val sessionIndex: UUID, val request: MeterValuesRequest) :
    ApplicationEvent(source)
