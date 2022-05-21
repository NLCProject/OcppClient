package test.client.requests

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.ocpp.client.client.interfaces.IClientInitService
import org.ocpp.client.client.interfaces.IClientRequestService
import org.ocpp.client.server.interfaces.IServerInitService
import org.springframework.beans.factory.annotation.Autowired

abstract class ClientRequestTest {

    @Autowired
    protected lateinit var serverInitService: IServerInitService

    @Autowired
    protected lateinit var clientInitService: IClientInitService

    @Autowired
    protected lateinit var clientRequestService: IClientRequestService

    protected val ipAddress = "127.0.0.1"

    @BeforeEach
    fun start() {
        serverInitService.init(ipAddress = ipAddress)
        clientInitService.init(ipAddress = ipAddress)
    }

    @AfterEach
    fun close() {
        serverInitService.close()
        clientInitService.disconnect()
    }
}
