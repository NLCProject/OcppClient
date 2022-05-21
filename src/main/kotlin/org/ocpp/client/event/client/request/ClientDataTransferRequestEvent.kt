package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.DataTransferRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming data transfer request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class ClientDataTransferRequestEvent(source: Any, val request: DataTransferRequest) : ApplicationEvent(source)
