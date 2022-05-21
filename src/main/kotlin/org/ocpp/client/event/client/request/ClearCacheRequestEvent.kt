package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.ClearCacheRequest
import org.springframework.context.ApplicationEvent

/**
 * Event is fired by an incoming clear cache request.
 *
 * @param request .
 * @param source Class where the event has been fired.
 */
class ClearCacheRequestEvent(source: Any, request: ClearCacheRequest) : ApplicationEvent(source)
