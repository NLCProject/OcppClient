package test.client.requests

import eu.chargetime.ocpp.model.core.RegistrationStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.server.request.BootNotificationRequestEvent
import org.ocpp.client.utils.Heartbeat
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class BootNotificationRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val chargePointVendor = Ids.getRandomIdString()
        val chargePointModel = Ids.getRandomIdString()
        val confirmation = clientRequestService.bootNotification(
            chargePointVendor = chargePointVendor,
            chargePointModel = chargePointModel
        )

        assertEquals(RegistrationStatus.Accepted, confirmation.status)
        assertEquals(Heartbeat.heartbeatInterval, confirmation.interval)

        val argumentCaptor = argumentCaptor<BootNotificationRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(chargePointVendor, argumentCaptor.firstValue.request.chargePointVendor)
        assertEquals(chargePointModel, argumentCaptor.firstValue.request.chargePointModel)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: BootNotificationRequestEvent) { }
    }
}
