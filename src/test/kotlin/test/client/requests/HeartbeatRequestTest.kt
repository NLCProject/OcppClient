package test.client.requests

import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.TestApplication
import org.ocpp.client.event.server.request.HeartbeatRequestEvent
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class HeartbeatRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val confirmation = clientRequestService.heartbeat()
        assertNotNull(confirmation.currentTime)

        val argumentCaptor = argumentCaptor<HeartbeatRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: HeartbeatRequestEvent) { }
    }
}
