package test.client.requests

import eu.chargetime.ocpp.model.core.ChargePointErrorCode
import eu.chargetime.ocpp.model.core.ChargePointStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.server.request.StatusNotificationRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class StatusNotificationRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var testEventListener: EventTestListener

    @Test
    fun sendRequest() {
        val connectorId = Ids.getRandomId()
        val errorCode = ChargePointErrorCode.values().random()
        val status = ChargePointStatus.values().random()
        clientRequestService.statusNotification(connectorId = connectorId, errorCode = errorCode, status = status)

        val argumentCaptor = argumentCaptor<StatusNotificationRequestEvent>()
        verify(testEventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(connectorId, argumentCaptor.firstValue.request.connectorId)
        assertEquals(errorCode, argumentCaptor.firstValue.request.errorCode)
        assertEquals(status, argumentCaptor.firstValue.request.status)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: StatusNotificationRequestEvent) { }
    }
}
