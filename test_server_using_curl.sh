#!/usr/bin/env bash

cert_dir=$(dirname "$0")/certs

curl https://localhost:8443/greeting --cert "$cert_dir/client-cert.pem" --key "$cert_dir/client-key-unencrypted.pem"
