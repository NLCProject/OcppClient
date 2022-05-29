package org.ocpp.client.event.server

import eu.chargetime.ocpp.model.SessionInformation
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired when the server has been connected successfully to socket.
 *
 * @param source Class where the event has been fired.
 * @param sessionIndex .
 * @param information .
 */
class ClientConnectedEvent(
    var sessionIndex: UUID,
    var information: SessionInformation,
    source: Any
) : ApplicationEvent(source)
