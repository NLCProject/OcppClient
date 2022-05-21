package org.ocpp.client.client.interfaces

/**
 * Start the OCPP client in client mode.
 */
interface IClientInitService {

    /**
     * Start OCPP client in client mode. The IP address is used to connect on the server.
     *
     * @param ipAddress IP address of the server socket to connect with.
     */
    fun init(ipAddress: String)
}
