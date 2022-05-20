package org.ocpp.client.server.interfaces

import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request

/**
 *
 */
interface IServerService {

    /**
     *
     */
    fun init(ipAddress: String)

    /**
     *
     */
    fun send(request: Request): Confirmation
}
