package org.ocpp.client.client.interfaces

import eu.chargetime.ocpp.model.core.KeyValueType

/**
 *
 */
interface IClientConfiguration {

    /**
     *
     */
    fun getConfiguration(): Array<KeyValueType>
}
