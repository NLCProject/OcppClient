package test.client.requests

import eu.chargetime.ocpp.model.core.MeterValue
import eu.chargetime.ocpp.model.core.SampledValue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.ocpp.client.TestApplication
import org.ocpp.client.event.server.request.MeterValuesRequestEvent
import org.ocpp.client.utils.DateTimeUtil
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.event.EventListener
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class MeterValuesRequestTest : ClientRequestTest() {

    @SpyBean
    private lateinit var eventListener: EventTestListener

    @Test
    fun sendRequest() {
        val sampleValueString = Ids.getRandomIdString()
        val sampledValue = SampledValue(sampleValueString)
        val meterValue = arrayOf(MeterValue(DateTimeUtil.dateNow(), arrayOf(sampledValue)))
        val connectorId = Ids.getRandomId()
        val transactionId = Ids.getRandomId()
        clientRequestService.meterValues(
            connectorId = connectorId,
            meterValue = meterValue,
            transactionId = transactionId
        )

        val argumentCaptor = argumentCaptor<MeterValuesRequestEvent>()
        verify(eventListener, times(1)).handle(argumentCaptor.capture())
        assertEquals(connectorId, argumentCaptor.firstValue.request.connectorId)
        assertEquals(transactionId, argumentCaptor.firstValue.request.transactionId)
        assertEquals(1, argumentCaptor.firstValue.request.meterValue.size)

        val receivedMeterValue = argumentCaptor.firstValue.request.meterValue.first()
        assertEquals(1, receivedMeterValue.sampledValue.size)
        assertNotNull(receivedMeterValue.timestamp)
        assertEquals(sampleValueString, receivedMeterValue.sampledValue.first().value)
    }

    @TestComponent
    class EventTestListener {
        @EventListener
        fun handle(event: MeterValuesRequestEvent) { }
    }
}
