
# OVERVIEW

This is a working demonstration of client certificate authentication using springboot 3 sslbundles.

It is based somewhat on `https://www.baeldung.com/spring-boot-security-ssl-bundles`.

## CLIENT

The client will use a cert we create to authenticate to the server.

## SERVER

The client keystore will contain the fwp certificate and key.

# SETUP

First, run `./install_certs.sh` which will:
- create then install the client key and cert in the `client` keystore (i.e. `client/src/main/resources`)
- install the client cert in the `server` truststore
- create then install the server key and cert in the `server` keystore (i.e. `server/src/main/resources`)
- install the server cert in the `client` truststore

Then in one window/tab cd into the `client` directory and run `mvn spring-boot:run` (or use the `./run-client.sh` convenience script).

Then in another window/tab cd into the `server` directory and run `mvn spring-boot:run` (or use the `./run-server.sh` convenience script).

And finally, in another window/tab run the `./test_client_using_curl.sh` script. This will invoke the client API, and the client will then invoke the server API with client cert auth.

If you're having trouble, run the `./test_server_using_curl.sh` to make sure the server is configured correctly. If it works, then the client is probably the problem.

# NOTES

- You don't need to add the client or server certs to the jvm cacerts or to the linux trusted certs
- PEM files don't appear to work with ssl bundles when specifying the direct path to the cert and key files, so we use JKS instead.
- 
- The client cert doesn't need to match the client/origin hostname
- You only need to add server cert (or internal corporate CA) to client truststore if the server cert isn't trusted by a well known CA (e.g. when signed with internal corporate CAs)
