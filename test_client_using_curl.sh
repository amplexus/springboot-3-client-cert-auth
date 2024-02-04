#!/usr/bin/env bash

function abort() {
	echo "ERROR: $1"
	exit 1
}

password=${1}

[ -n "$password" ] || abort "Missing password - you need to extract the password from the running client log - look for the 'Using generated security password' line"

curl -k https://user:$password@localhost:8444/hello
