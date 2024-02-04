#!/usr/bin/env bash

debug=${1:-false}

function abort() {
	echo "ERROR: $1"
	exit 1
}

client_dir=$(dirname "$0")/client

[ -f "$client_dir/src/main/resources/client-cert.p12" ] || abort "Missing client cert in src/main/resources - did you run install-certs.sh?"

[ "$debug" == "true" ] && EXTRA_OPTS=-Dspring-boot.run.jvmArguments="-Djavax.net.debug=ssl:handshake:trustmanager:keymanager"

cd "$client_dir" &&
	mvn spring-boot:run $EXTRA_OPTS 2>&1 | tee client.log
