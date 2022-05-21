package org.ocpp.client.event.server

import org.springframework.context.ApplicationEvent

/**
 * Event is fired when the server has lost its current session.
 *
 * @param source Class where the event has been fired.
 */
class ServerSessionLostEvent(source: Any) : ApplicationEvent(source)
