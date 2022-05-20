package org.ocpp.client.utils

import java.util.*
import kotlin.random.Random

/**
 *
 */
object Ids {

    /**
     *
     */
    fun getRandomId(): String = UUID.randomUUID().toString()

    /**
     *
     */
    fun getRandomUUID(): UUID = UUID.randomUUID()

    /**
     *
     */
    fun getRandomIdentifier(): Int = Random.nextInt(from = 100_000, until = 999_999)
}
