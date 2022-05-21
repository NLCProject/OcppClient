package test.client

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.ocpp.client.Application
import org.ocpp.client.client.interfaces.IClientInitService
import org.ocpp.client.event.client.ClientConnectedEvent
import org.ocpp.client.event.client.ClientConnectionLostEvent
import org.ocpp.client.event.server.ServerConnectedEvent
import org.ocpp.client.event.server.ServerSessionLostEvent
import org.ocpp.client.server.interfaces.IServerInitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class ClientInitTest {

    @Autowired
    private lateinit var serverInitService: IServerInitService

    @Autowired
    private lateinit var service: IClientInitService

    @SpyBean
    private lateinit var testEventListener: EventTestListener

    @AfterEach
    fun close() {
        serverInitService.close()
        service.disconnect()
    }

    @Test
    fun init() {
        val ipAddress = "127.0.0.1"
        serverInitService.init(ipAddress = ipAddress)
        service.init(ipAddress = ipAddress)
        verify(testEventListener, times(1)).handleClientConnect(anyOrNull())
        verify(testEventListener, times(1)).handleServerConnect(anyOrNull())
        verify(testEventListener, never()).handleClose(anyOrNull())
        verify(testEventListener, never()).handleServerSessionLost(anyOrNull())
    }

    @Test
    fun init_thenDisconnect() {
        val ipAddress = "127.0.0.1"
        serverInitService.init(ipAddress = ipAddress)
        service.init(ipAddress = ipAddress)
        verify(testEventListener, times(1)).handleClientConnect(anyOrNull())
        verify(testEventListener, times(1)).handleServerConnect(anyOrNull())
        verify(testEventListener, never()).handleClose(anyOrNull())
        verify(testEventListener, never()).handleServerSessionLost(anyOrNull())

        service.disconnect()
        verify(testEventListener, times(1)).handleClose(anyOrNull())
        verify(testEventListener, times(1)).handleServerSessionLost(anyOrNull())
    }

    @Test
    fun init_serverNotStarted() {
        val ipAddress = "127.0.0.1"
        service.init(ipAddress = ipAddress)
        verify(testEventListener, never()).handleClientConnect(anyOrNull())
        verify(testEventListener, times(1)).handleClose(anyOrNull())
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handleClose(event: ClientConnectionLostEvent) { }

        @EventListener
        fun handleClientConnect(event: ClientConnectedEvent) { }

        @EventListener
        fun handleServerConnect(event: ServerConnectedEvent) { }

        @EventListener
        fun handleServerSessionLost(event: ServerSessionLostEvent) { }
    }
}
