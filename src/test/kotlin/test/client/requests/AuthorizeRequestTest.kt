package test.client.requests

import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.server.request.AuthorizeRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class AuthorizeRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val idTag = Ids.getRandomIdString()
        clientRequestService.authorize(idTag = idTag)

        val argumentCaptor = argumentCaptor<AuthorizeRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(idTag, argumentCaptor.firstValue.request.idTag)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: AuthorizeRequestEvent) { }
    }
}
