package test.server.requests

import eu.chargetime.ocpp.model.core.UnlockStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.TestApplication
import org.ocpp.client.event.client.request.UnlockConnectorRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class UnlockConnectorRequestTest : ServerRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val connectorId = Ids.getRandomId()
        val confirmation = serverRequestService.unlockConnector(connectorId = connectorId)
        assertEquals(UnlockStatus.Unlocked, confirmation.status)

        val argumentCaptor = argumentCaptor<UnlockConnectorRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(connectorId, argumentCaptor.firstValue.request.connectorId)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: UnlockConnectorRequestEvent) { }
    }
}
