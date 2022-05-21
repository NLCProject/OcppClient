package test.server.requests

import eu.chargetime.ocpp.model.core.ClearCacheStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.client.request.ClearCacheRequestEvent
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class ClearCacheRequestTest : ServerRequestTest() {

    @SpyBean
    private lateinit var testEventListener: EventTestListener

    @Test
    fun sendRequest() {
        val confirmation = serverRequestService.clearCache()
        assertEquals(ClearCacheStatus.Accepted, confirmation.status)

        val argumentCaptor = argumentCaptor<ClearCacheRequestEvent>()
        verify(testEventListener, times(1)).handle(argumentCaptor.capture())
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: ClearCacheRequestEvent) { }
    }
}
