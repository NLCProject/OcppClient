package test.client.requests

import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.TestApplication
import org.ocpp.client.event.server.request.StopTransactionRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class StopTransactionRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val meterStop = Ids.getRandomId()
        val transactionId = Ids.getRandomId()
        val confirmation = clientRequestService.stopTransaction(meterStop = meterStop, transactionId = transactionId)
        assertNull(confirmation.idTagInfo)

        val argumentCaptor = argumentCaptor<StopTransactionRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(meterStop, argumentCaptor.firstValue.request.meterStop)
        assertEquals(transactionId, argumentCaptor.firstValue.request.transactionId)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: StopTransactionRequestEvent) { }
    }
}
