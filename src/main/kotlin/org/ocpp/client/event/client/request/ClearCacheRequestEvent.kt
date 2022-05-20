package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.ClearCacheRequest
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class ClearCacheRequestEvent(source: Any, request: ClearCacheRequest) : ApplicationEvent(source)
