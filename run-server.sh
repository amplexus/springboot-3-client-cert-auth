#!/usr/bin/env bash

debug=${1:-false}

function abort() {
	echo "ERROR: $1"
	exit 1
}

server_dir=$(dirname "$0")/server

[ -f "$server_dir/src/main/resources/client-cert.pem" ] || abort "Missing client cert in src/main/resources - did you run install-certs.sh?"
[ -f "$server_dir/src/main/resources/server-cert.pem" ] || abort "Missing server cert in src/main/resources - did you run install-certs.sh?"
[ -f "$server_dir/src/main/resources/server-key-unencrypted.pem" ] || abort "Missing server key in src/main/resources - did you run install-certs.sh?"

[ "$debug" == "true" ] && EXTRA_OPTS=-Dspring-boot.run.jvmArguments="-Djavax.net.debug=ssl:handshake:trustmanager:keymanager"
[ "$debug" == "true" ] && echo "Enabling debug"

cd "$server_dir" &&
	mvn spring-boot:run $EXTRA_OPTS 2>&1 | tee server.log
