package org.ocpp.client.application

/**
 *
 */
interface IOcppClient {

    /**
     *
     */
    fun startClient(ipAddress: String)

    /**
     *
     */
    fun startServer(ipAddress: String)
}
