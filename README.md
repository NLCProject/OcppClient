# OCPP Client

## About this library
This library is used by the charge point and backend for bi-directional communication. This library can be used either in client or
server mode and handles all outgoing and incoming requests.

The core functionality provides a bi-directional communication between one server and multiple clients. The 
communication is based on a simple event architecture. Every request (regardless if server or client) is communicated
via an event and can be handled by the registered application. How to install and use this libray is described in detail
in the upcoming topics.

## Installation
### Create Artifact
<ul>
    <li>In the toolbar, go to <b>Build > Build Artifacts.. > Rebuild</b></li>
    <li>Thr JAR output can be found in the folder <b>out > artifacts > OcppClient > *.jar</b></li>
</ul>

### Implement Library (Artifact) in other application
<ul>
    <li>Create the folder <b>libs</b> in your root folder and copy the JAR file into it. Do this in the application where you want to use this library.</li>
    <li>Open the build.gradle file.</li>
    <li>Add the following dependency:</li>

            ```
                dependencies {
                    compile fileTree(dir: 'libs', includes: ['*.jar'])
                }
            ```

<li>Add the following repository:</li>

            ```
                flatDir {
                    dirs 'libs'
                }
            ```

<li>Reload your Gradle project. Now you can access the JAR file.</li>
<li>Every time you use a new version of this lib, you only have to replace the JAR file in the <b>libs</b> folder and reload the Gradle project</li>
</ul>

## Usage of this library
### Register as server
<ul>
    <li>Autowire the OcppClient interface.</li>
    <li>Call the 'startServer' method with the IP address, on which the server should open its socket. The port is always <b>8887</b>.</li>
</ul>

    ```
        @Autowired
        private lateinit var ocppClient: IOcppClient

        fun startServer(ipAddress: String) {
            ocppClient.startServer(ipAddress)
        }
    ```

### Send request as server
Autowire the file <b>IServerRequestService</b>. In this files, all available requests can be found. Every request runs
async and waits for the response from the client. Each request returns the confirmation from the client. The following requests
are supported:
<ul>
    <li>Change Availability</li>
    <li>Get Configuration</li>
    <li>Change Configuration</li>
    <li>Clear Cache</li>
    <li>Data Transfer</li>
    <li>Remote Start Transaction</li>
    <li>Remote Stop Transaction</li>
    <li>Reset</li>
    <li>Unlock Connector</li>
</ul>

### Handle client requests as server
In order to handle incoming requests of a connected client, the server must listen for the respective event which is
fired by requesting the functionality. For every client functionality (as listed below), the respective event is
available. To handle it, just announce an event listener like this

    ```
        /**
         * Authorize smart home.
         *
         * @param event .
         */
        @EventListener(AuthorizeRequestEvent::class)
        fun authorize(event: AuthorizeRequestEvent)
    ```

### Register as client
<ul>
    <li>Autowire the OcppClient interface.</li>
    <li>Call the 'startClient' method with the IP address of the server socket. The port is always <b>8887</b>.</li>
</ul>

    ```
        @Autowired
        private lateinit var ocppClient: IOcppClient

        fun startClient(ipAddress: String) {
            ocppClient.startClient(ipAddress)
        }
    ```

### Send request as client
Autowire the file <b>IClientRequestService</b>. In this files, all available requests can be found. Every request runs
async and waits for the response from the server. Each request returns the confirmation from the server. The following requests
are supported:
<ul>
    <li>Authorize</li>
    <li>Data Transfer</li>
    <li>Heartbeat</li>
    <li>Start Transaction</li>
    <li>Stop Transaction</li>
    <li>Boot Notification</li>
    <li>Meter Values</li>
    <li>Status Notification</li>
</ul>

### Handle server requests as client
In order to handle incoming requests of a connected server, the client must listen for the respective event which is
fired by requesting the functionality. For every server functionality (as listed above), the respective event is
available. To handle it, just announce an event listener like this

    ```
        /**
         * Changes availability of a connector.
         *
         * @param event .
         */
        @EventListener(ChangeAvailabilityRequestEvent::class)
        fun changeAvailability(event: ChangeAvailabilityRequestEvent)
    ```

## Credits
<ul>
    <li>Markus Graf</li>
    <li>Alex Gill</li>
    <li>Ivan Krylov</li>
</ul>

## License
The ISC jar library which is used in this project was written by Markus Graf.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the “Software”), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Literature
<ul>
    <li>https://github.com/NLCProject/OcppServer</li>
    <li>https://github.com/NLCProject/OcppSmartHome</li>
    <li>https://github.com/NLCProject/BatteryController</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Getting-started</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Setting-up-v1.6-OCPP-J-server</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Setting-up-v1.6-OCPP-J-client</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/tree/master/ocpp-v1_6/src/main/java/eu/chargetime/ocpp</li>
</ul>
