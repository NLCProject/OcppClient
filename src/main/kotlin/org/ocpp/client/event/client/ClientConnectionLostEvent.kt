package org.ocpp.client.event.client

import org.springframework.context.ApplicationEvent

/**
 * Event is fired when the client lost its current connection.
 *
 * @param source Class where the event has been fired.
 */
class ClientConnectionLostEvent(source: Any) : ApplicationEvent(source)
