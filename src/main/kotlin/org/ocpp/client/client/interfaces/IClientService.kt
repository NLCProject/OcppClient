package org.ocpp.client.client.interfaces

import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request

/**
 *
 */
interface IClientService {

    /**
     *
     */
    fun init(ipAddress: String)

    /**
     *
     */
    fun send(request: Request): Confirmation
}
