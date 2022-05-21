package test.server.requests

import eu.chargetime.ocpp.model.core.AvailabilityType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.client.request.ChangeAvailabilityRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class ChangeAvailabilityRequestTest: ServerRequestTest() {

    @SpyBean
    private lateinit var testEventListener: EventTestListener

    @Test
    fun init() {
        val connectorId = Ids.getRandomId()
        val type = AvailabilityType.values().random()
        serverRequestService.changeAvailability(connectorId = connectorId, type = type)

        val argumentCaptor = argumentCaptor<ChangeAvailabilityRequestEvent>()
        verify(testEventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(connectorId, argumentCaptor.firstValue.request.connectorId)
        assertEquals(type, argumentCaptor.firstValue.request.type)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: ChangeAvailabilityRequestEvent) { }
    }
}
