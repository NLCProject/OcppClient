package test.server.requests

import eu.chargetime.ocpp.model.core.RemoteStartStopStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.TestApplication
import org.ocpp.client.event.client.request.RemoteStartTransactionRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class RemoteStartTransactionRequestTest : ServerRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val connectorId = Ids.getRandomId()
        val idTag = Ids.getRandomIdString()
        val confirmation = serverRequestService.remoteStartTransaction(
            connectorId = connectorId,
            idTag = idTag,
            profile = null
        )

        assertEquals(RemoteStartStopStatus.Accepted, confirmation.status)

        val argumentCaptor = argumentCaptor<RemoteStartTransactionRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(connectorId, argumentCaptor.firstValue.request.connectorId)
        assertEquals(idTag, argumentCaptor.firstValue.request.idTag)
        assertNull(argumentCaptor.firstValue.request.chargingProfile)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: RemoteStartTransactionRequestEvent) { }
    }
}
