package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.RemoteStopTransactionRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class RemoteStopTransactionRequestEvent(source: Any, request: RemoteStopTransactionRequest) :
    ApplicationEvent(source)
