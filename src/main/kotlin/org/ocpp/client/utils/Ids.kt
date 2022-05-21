package org.ocpp.client.utils

import java.util.*
import kotlin.random.Random

/**
 * Numeric Util
 */
object Ids {

    /**
     * Returns a random ID as string.
     *
     * @return Random ID as string
     */
    fun getRandomIdString(): String = getRandomId().toString()

    /**
     * Returns a random integer between 100_000 and 999_999.
     *
     * @return Random integer.
     */
    fun getRandomId(): Int = Random.nextInt(from = 100_000, until = 999_999)

    /**
     * Returns a random UUID.
     *
     * @return UUID.
     */
    fun getRandomUUID(): UUID = UUID.randomUUID()
}
