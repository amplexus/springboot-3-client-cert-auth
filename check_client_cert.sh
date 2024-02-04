#!/usr/bin/env bash

client_dir=$(dirname "$0")/client

keytool -list -v -keystore "$client_dir/src/main/resources/client-cert.p12" -storepass secret -storetype PKCS12
