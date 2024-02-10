
# OVERVIEW

This is a working demonstration of client certificate authentication using [springboot 3 sslbundles](https://spring.io/blog/2023/06/07/securing-spring-boot-applications-with-ssl).

It is based somewhat on `https://www.baeldung.com/spring-boot-security-ssl-bundles`.

In this demo, we have two web service apps:
1. The "server" app which listens on port 8443 and requires client certificate authentication
2. The "client" app which listens on port 8444 and uses client certificate authentication to communicate with the above server

The client has a springboot restful service that communicates with the server's restful service that is protected with client certificate authentication.

The `./install_certs.sh` script is used to generate and install the certificates into both the client and server `src/main/resources` directories. Essentially it does the following:
- creates client cert and key and installs them in the client keystore (which it creates and protects with a password)
- installs the client cert in the server truststore
- creates server cert and key and installs them in the server resources folder (they are referenced by filename in the server's application.yaml)
- installs the server cert in the client truststore

The client and the server will pick their respective keystores and truststores up at launch time and use them for client certificate authentication.

## PRE REQUISITES

- Java 17+
- Maven 3

## SETUP

After cloning this repo, run `./install_certs.sh` to create the certificates, keys, keystores and truststores.

## RUN THE DEMO

In one window/tab, `cd` into the `client` directory and run `mvn spring-boot:run` (or use the `./run-client.sh` convenience script).

Then in another window/tab `cd` into the `server` directory and run `mvn spring-boot:run` (or use the `./run-server.sh` convenience script).

And finally, in another window/tab run the `./test_client_using_curl.sh` script. This will invoke the client API, and the client will then invoke the server API with client cert auth.

You can independently verify the server using curl by running `./test_server_using_curl.sh`.

## TESTING

You can run `mvn test` on the client and there is no need to install certificates as the tests yaml doesn't specify any sslbundles. 

I didn't create any tests for the server as the focus for me was on the client implementation and unit testing.

## NOTES

- You don't need to add the client or server certs to the jvm cacerts or to the linux trusted certs
- The client cert doesn't need to match the client/origin hostname
- PEM files don't appear to work with ssl bundles when specifying the direct path to the cert and key files, so we use JKS instead.
- You only need to add server cert (or internal corporate CA) to client truststore if the server cert isn't trusted by a well known CA (e.g. when signed with internal corporate CAs)
