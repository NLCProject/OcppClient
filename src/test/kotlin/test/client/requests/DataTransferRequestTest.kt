package test.client.requests

import eu.chargetime.ocpp.model.core.DataTransferStatus
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.TestApplication
import org.ocpp.client.event.server.request.ServerDataTransferRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class DataTransferRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val vendorId = Ids.getRandomIdString()
        val data = Ids.getRandomIdString()
        val confirmation = clientRequestService.dataTransfer(vendorId = vendorId, data = data)
        assertEquals(DataTransferStatus.Accepted, confirmation.status)
        assertNull(confirmation.data)

        val argumentCaptor = argumentCaptor<ServerDataTransferRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(vendorId, argumentCaptor.firstValue.request.vendorId)
        assertEquals(data, argumentCaptor.firstValue.request.data)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: ServerDataTransferRequestEvent) { }
    }
}
