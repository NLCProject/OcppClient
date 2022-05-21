package org.ocpp.client.client.interfaces

import eu.chargetime.ocpp.model.core.KeyValueType

/**
 * This service has to be implemented by the client application to provide it's configuration.
 */
interface IClientConfiguration {

    /**
     * Gets configuration.
     *
     * @return Array of key value pairs.
     */
    fun getConfiguration(): Array<KeyValueType>
}
