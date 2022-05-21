package org.ocpp.client.application

/**
 * This service is used to start the OCPP client either in server or client mode.
 */
interface IOcppClient {

    /**
     * Start OCPP client in client mode. The IP address is used to connect on the server.
     *
     * @param ipAddress IP address of the server socket to connect with.
     */
    fun startClient(ipAddress: String)

    /**
     * Start OCPP client in server mode. The IP address is used to create the socket connection.
     *
     * @param ipAddress IP address of the local socket connection.
     */
    fun startServer(ipAddress: String)
}
