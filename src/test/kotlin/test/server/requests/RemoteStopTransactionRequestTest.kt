package test.server.requests

import eu.chargetime.ocpp.model.core.RemoteStartStopStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.client.request.RemoteStopTransactionRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class RemoteStopTransactionRequestTest : ServerRequestTest() {

    @SpyBean
    private lateinit var testEventListener: EventTestListener

    @Test
    fun sendRequest() {
        val transactionId = Ids.getRandomId()
        val confirmation = serverRequestService.remoteStopTransaction(transactionId = transactionId)
        assertEquals(RemoteStartStopStatus.Accepted, confirmation.status)

        val argumentCaptor = argumentCaptor<RemoteStopTransactionRequestEvent>()
        verify(testEventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(transactionId, argumentCaptor.firstValue.request.transactionId)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: RemoteStopTransactionRequestEvent) { }
    }
}
