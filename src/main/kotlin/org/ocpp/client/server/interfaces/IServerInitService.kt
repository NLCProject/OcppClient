package org.ocpp.client.server.interfaces

/**
 * Start the OCPP client in server mode.
 */
interface IServerInitService {

    /**
     * Start OCPP client in server mode. The IP address is used to create the socket connection.
     *
     * @param ipAddress IP address of the local socket connection.
     */
    fun init(ipAddress: String)
}
