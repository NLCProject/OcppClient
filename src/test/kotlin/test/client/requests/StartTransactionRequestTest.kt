package test.client.requests

import eu.chargetime.ocpp.model.core.AuthorizationStatus
import eu.chargetime.ocpp.model.core.IdTagInfo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.Application
import org.ocpp.client.event.server.request.StartTransactionRequestEvent
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class StartTransactionRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val idTag = Ids.getRandomIdString()
        val connectorId = Ids.getRandomId()
        val meterStart = Ids.getRandomId()
        val confirmation = clientRequestService.startTransaction(
            idTag = idTag,
            meterStart = meterStart,
            connectorId = connectorId
        )

        assertEquals(IdTagInfo(AuthorizationStatus.Accepted), confirmation.idTagInfo)
        assertTrue(confirmation.transactionId in 100_000..999_999)

        val argumentCaptor = argumentCaptor<StartTransactionRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(idTag, argumentCaptor.firstValue.request.idTag)
        assertEquals(meterStart, argumentCaptor.firstValue.request.meterStart)
        assertEquals(connectorId, argumentCaptor.firstValue.request.connectorId)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: StartTransactionRequestEvent) { }
    }
}
