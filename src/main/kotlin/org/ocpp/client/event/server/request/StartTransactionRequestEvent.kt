package org.ocpp.client.event.server.request

import eu.chargetime.ocpp.model.core.StartTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 * Event is fired by an incoming start transaction heartbeat request.
 *
 * @param source Class where the event has been fired.
 * @param sessionIndex .
 * @param transactionId .
 * @param request .
 */
class StartTransactionRequestEvent(
    source: Any,
    val sessionIndex: UUID,
    val transactionId: Int,
    val request: StartTransactionRequest
) : ApplicationEvent(source)
