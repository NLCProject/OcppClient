package org.ocpp.client.utils

import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Util for time handling.
 */
object DateTimeUtil {

    /**
     * Returns the current ZonedDateTime of the zone ID 'Europe/Vienna'.
     *
     * @return Zoned date time.
     */
    fun dateNow(): ZonedDateTime {
        return ZonedDateTime.now(ZoneId.of("Europe/Vienna"))
    }
}
