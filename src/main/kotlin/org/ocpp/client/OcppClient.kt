package org.ocpp.client

import org.ocpp.client.application.IOcppClient
import org.ocpp.client.client.interfaces.IClientService
import org.ocpp.client.server.interfaces.IServerService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class OcppClient : IOcppClient {

    @Autowired
    private lateinit var client: IClientService

    @Autowired
    private lateinit var server: IServerService

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var started = false

    override fun startClient(ipAddress: String) {
        logger.info("Starting OCPP client")
        verifyIfStartedAndThrow()
        start()
        client.init(ipAddress = ipAddress)
        logger.info("OCPP client started")
    }

    override fun startServer(ipAddress: String) {
        logger.info("Starting OCPP server")
        verifyIfStartedAndThrow()
        start()
        server.init(ipAddress = ipAddress)
        logger.info("OCPP client server")
    }

    private fun verifyIfStartedAndThrow() {
        if (started)
            throw Exception("OCPP client already started")
    }

    private fun start() {
        started = true
    }
}
