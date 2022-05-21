package test.server.requests

import eu.chargetime.ocpp.model.core.ConfigurationStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.client.request.ChangeConfigurationRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class ChangeConfigurationRequestTest : ServerRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val key = Ids.getRandomIdString()
        val value = Ids.getRandomIdString()
        val confirmation = serverRequestService.changeConfiguration(key = key, value = value)
        assertEquals(ConfigurationStatus.Accepted, confirmation.status)

        val argumentCaptor = argumentCaptor<ChangeConfigurationRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(key, argumentCaptor.firstValue.request.key)
        assertEquals(value, argumentCaptor.firstValue.request.value)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: ChangeConfigurationRequestEvent) { }
    }
}
