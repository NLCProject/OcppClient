package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.UnlockConnectorRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class UnlockConnectorRequestEvent(source: Any, request: UnlockConnectorRequest) : ApplicationEvent(source)
