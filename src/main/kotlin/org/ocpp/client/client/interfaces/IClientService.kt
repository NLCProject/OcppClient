package org.ocpp.client.client.interfaces

import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request

/**
 * Service to send requests to the server. Every request will wait for the response from the server and returns an async
 * confirmation.
 */
interface IClientService {

    /**
     * Send a request to the server. This method wait until a response is received.
     *
     * @param request Request to send.
     * @return Async confirmation.
     */
    fun send(request: Request): Confirmation
}
