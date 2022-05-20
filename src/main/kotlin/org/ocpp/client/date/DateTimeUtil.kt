package org.ocpp.client.date

import java.time.ZoneId
import java.time.ZonedDateTime

/**
 *
 */
object DateTimeUtil {

    /**
     *
     */
    fun dateNow(): ZonedDateTime {
        return ZonedDateTime.now(ZoneId.of("Europe/Vienna"))
    }
}
