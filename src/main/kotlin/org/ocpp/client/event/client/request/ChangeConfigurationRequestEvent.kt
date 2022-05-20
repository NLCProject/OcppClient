package org.ocpp.client.event.client.request

import eu.chargetime.ocpp.model.core.*
import org.springframework.context.ApplicationEvent
import java.util.*

/**
 *
 */
class ChangeConfigurationRequestEvent(source: Any, request: ChangeConfigurationRequest) : ApplicationEvent(source)
