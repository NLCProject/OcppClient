package org.ocpp.client.utils

import java.util.*
import kotlin.random.Random

/**
 * Numeric Util
 */
object Ids {

    /**
     * Returns a random UUID as string.
     *
     * @return UUID as string
     */
    fun getRandomId(): String = UUID.randomUUID().toString()

    /**
     * Returns a random UUID.
     *
     * @return UUID.
     */
    fun getRandomUUID(): UUID = UUID.randomUUID()

    /**
     * Returns a random integer between 100_000 and 999_999.
     *
     * @return Random integer.
     */
    fun getRandomIdentifier(): Int = Random.nextInt(from = 100_000, until = 999_999)
}
