#!/usr/bin/env bash

client_dir=$(dirname "$0")/client

openssl pkcs12 -nokeys -info -in $client_dir/src/main/resources/server-cert.p12 -passin pass:secret
