package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.DataTransferRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming data transfer request.
 *
 * @param sessionIndex .
 * @param request .
 * @param source Class where the event has been fired.
 */
class ServerDataTransferRequestEvent(source: Any, val sessionIndex: UUID, val request: DataTransferRequest) :
    ApplicationEvent(source)
