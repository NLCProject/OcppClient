# OCPP Client
This library is used by the charge point and backend for communication. This client can be used either in client or
server mode and handles all outgoing and incoming requests.

## Installation
### Create Artifact
<ul>
    <li>In the toolbar, go to 'Build > Build Artifacts.. > Rebuild'</li>
    <li>Thr JAR output can be found in the folder 'out > artifacts > OcppClient > *.jar'</li>
</ul>

### Implement Library (Artifact)
<ul>
    <li>Create the folder <b>libs</b> in your root folder and copy the JAR file into it.</li>
    <li>Open the build.gradle file in the application where you want to use this library.</li>
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
async and waits for the response from the client. Each request returns the confirmation from the client.

### Register as client
<ul>
    <li>Autowire the OcppClient interface.</li>
    <li>Call the 'startClient' method with the IP address of the server socket. The port is always <b>8887</b>.</li>
    <li>Every client has to implement the <b>IClientConfiguration</b> interface to provide its configuration in case the server requests it.</li>
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
async and waits for the response from the server. Each request returns the confirmation from the server.

## Credits
<ul>
    <li>Markus Graf</li>
    <li>Alex Gill</li>
    <li>Ivan Krylov</li>
</ul>

## License
None

## Literature
<ul>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Getting-started</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Setting-up-v1.6-OCPP-J-server</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/wiki/Setting-up-v1.6-OCPP-J-client</li>
    <li>https://github.com/ChargeTimeEU/Java-OCA-OCPP/tree/master/ocpp-v1_6/src/main/java/eu/chargetime/ocpp</li>
</ul>
