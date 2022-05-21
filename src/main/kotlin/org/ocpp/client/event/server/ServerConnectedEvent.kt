package org.ocpp.client.event.server

import org.springframework.context.ApplicationEvent

/**
 * Event is fired when the server has been connected successfully to socket.
 *
 * @param source Class where the event has been fired.
 */
class ServerConnectedEvent(source: Any) : ApplicationEvent(source)
