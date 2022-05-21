package org.ocpp.client

import org.ocpp.client.application.IOcppClient
import org.ocpp.client.client.interfaces.IClientInitService
import org.ocpp.client.server.interfaces.IServerInitService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OcppClient : IOcppClient {

    @Autowired
    private lateinit var client: IClientInitService

    @Autowired
    private lateinit var server: IServerInitService

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun startClient(ipAddress: String) {
        logger.info("Starting OCPP client")
        client.init(ipAddress = ipAddress)
        logger.info("OCPP client started")
    }

    override fun startServer(ipAddress: String) {
        logger.info("Starting OCPP server")
        server.init(ipAddress = ipAddress)
        logger.info("OCPP client server")
    }
}
