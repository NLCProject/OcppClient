package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class RemoteStartTransactionRequestEvent(source: Any, request: RemoteStartTransactionRequest) :
    ApplicationEvent(source)
