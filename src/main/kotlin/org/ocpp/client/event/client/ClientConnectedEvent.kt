package org.ocpp.client.event.client

import org.springframework.context.ApplicationEvent

/**
 * Event is fired when the client has been connected successfully.
 *
 * @param source Class where the event has been fired.
 */
class ClientConnectedEvent(source: Any) : ApplicationEvent(source)
