package org.ocpp.client.server.interfaces

/**
 * Start the OCPP client in server mode.
 */
interface IServerInitService {

    /**
     * Start OCPP client in server mode. The IP address is used to create the socket connection.
     *
     * @param ipAddress IP address of the local socket connection.
     * @param testMode Run in test mode to use initial session index for sending requests
     */
    fun init(ipAddress: String, testMode: Boolean = false)

    /**
     * Close connection if open
     */
    fun close()
}
