package org.ocpp.client.server.interfaces

import eu.chargetime.ocpp.model.Confirmation
import eu.chargetime.ocpp.model.Request

/**
 * Service to send requests to the client. Every request will wait for the response from the client and returns an async
 * confirmation.
 */
interface IServerService {

    /**
     * Send a request to the client. This method wait until a response is received.
     *
     * @param request Request to send.
     * @param sessionIndex Session index of receiver client.
     * @return Async confirmation.
     */
    fun send(request: Request, sessionIndex: String): Confirmation
}
