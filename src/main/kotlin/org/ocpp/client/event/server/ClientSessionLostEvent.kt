package org.ocpp.client.event.server

import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired when the server has lost its current session.
 *
 * @param source Class where the event has been fired.
 * @param sessionIndex .
 */
class ClientSessionLostEvent(var sessionIndex: UUID, source: Any) : ApplicationEvent(source)
