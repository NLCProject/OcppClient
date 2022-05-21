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
    private lateinit var eventListener: EventTestListener

    private val ipAddress = "127.0.0.1"

    @AfterEach
    fun close() {
        serverInitService.close()
        service.disconnect()
    }

    @Test
    fun init() {
        serverInitService.init(ipAddress = ipAddress)
        service.init(ipAddress = ipAddress)
        verify(eventListener, times(1)).handleClientConnect(anyOrNull())
        verify(eventListener, times(1)).handleServerConnect(anyOrNull())
        verify(eventListener, never()).handleClose(anyOrNull())
        verify(eventListener, never()).handleServerSessionLost(anyOrNull())
    }

    @Test
    fun init_thenDisconnect() {
        serverInitService.init(ipAddress = ipAddress)
        service.init(ipAddress = ipAddress)
        verify(eventListener, times(1)).handleClientConnect(anyOrNull())
        verify(eventListener, times(1)).handleServerConnect(anyOrNull())
        verify(eventListener, never()).handleClose(anyOrNull())
        verify(eventListener, never()).handleServerSessionLost(anyOrNull())

        service.disconnect()
        verify(eventListener, times(1)).handleClose(anyOrNull())
        verify(eventListener, times(1)).handleServerSessionLost(anyOrNull())
    }

    @Test
    fun init_thenServerCloses() {
        serverInitService.init(ipAddress = ipAddress)
        service.init(ipAddress = ipAddress)
        verify(eventListener, times(1)).handleClientConnect(anyOrNull())
        verify(eventListener, times(1)).handleServerConnect(anyOrNull())
        verify(eventListener, never()).handleClose(anyOrNull())
        verify(eventListener, never()).handleServerSessionLost(anyOrNull())

        serverInitService.close()
        verify(eventListener, times(1)).handleClose(anyOrNull())
        verify(eventListener, times(1)).handleServerSessionLost(anyOrNull())
    }

    @Test
    fun init_serverNotStarted() {
        service.init(ipAddress = ipAddress)
        verify(eventListener, never()).handleClientConnect(anyOrNull())
        verify(eventListener, times(1)).handleClose(anyOrNull())
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
