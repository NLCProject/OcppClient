package test.server.requests

import org.junit.jupiter.api.Test
import org.ocpp.client.TestApplication
import org.ocpp.client.utils.Ids
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TestApplication::class])
class GetConfigurationRequestTest : ServerRequestTest() {

    @Test
    fun sendRequest() {
        val keys = arrayOf(Ids.getRandomIdString(), Ids.getRandomIdString())
        val confirmation = serverRequestService.getConfiguration(keys = keys)
        assertEquals(1, confirmation.configurationKey.size)
        assertTrue(confirmation.configurationKey.first().readonly)
        assertEquals("TestKey", confirmation.configurationKey.first().key)
    }
}
