#!/usr/bin/env bash

function abort() {
	echo "ERROR: $1"
	exit 2
}

certs_dir=$(dirname "$0")/certs
client_dir=$(dirname "$0")/client
server_dir=$(dirname "$0")/server

mkdir -p "$certs_dir"

rm -f "$client_dir"/src/main/resources/*.p12
rm -f "$client_dir"/src/main/resources/*.pem
rm -f "$client_dir"/src/main/resources/*.jks
rm -f "$server_dir"/src/main/resources/*.p12
rm -f "$server_dir"/src/main/resources/*.pem
rm -f "$server_dir"/src/main/resources/*.jks

echo "Creating client X509 key and cert"
openssl req -x509 -newkey rsa:4096 -keyout "$certs_dir/client-key-unencrypted.pem" -out "$certs_dir/client-cert.pem" -sha256 -days 3650 -nodes -passout pass:secret \
	-subj "/C=AU/ST=New South Wales/L=Sydney/O=Acme Ltd/OU=Accounts Dept/CN=kubuntu"

echo "Converting client X509 cert to PKCS12 because springboot w/ sslbundles doesnt like PEMs"
openssl pkcs12 -export -in "$certs_dir/client-cert.pem" -inkey "$certs_dir/client-key-unencrypted.pem" -out "$client_dir/src/main/resources/client-cert.p12" -name "kubuntu" -passout pass:secret

echo "Generating server X509 key and cert"
openssl req -x509 -newkey rsa:4096 -keyout "$certs_dir/server-key-unencrypted.pem" -out "$certs_dir/server-cert.pem" -sha256 -days 3650 -nodes -passout pass:secret \
	-subj "/C=AU/ST=New South Wales/L=Mount Druitt/O=Acme Ltd/OU=Sales Dept/CN=localhost" # CN=localhost -> must match the server domain referenced in client code
# Per https://stackoverflow.com/questions/77537332/mutualtls-with-springboot-sslbundles-and-resttemplate springboot/jvm doesnt like openssl created truststores
# openssl pkcs12 -export -in "$certs_dir/server-cert.pem" -nokeys -out "$client_dir/src/main/resources/server-cert.p12" -name "localhost"
echo "Converting server X509 cert to JKS truststore for the client because springboot w/ sslbundles doesnt like PEMs"
keytool -noprompt -keystore "$client_dir/src/main/resources/truststore.jks" -alias localhost -import -file "$certs_dir/server-cert.pem" -storepass 'secret'

echo "Installing client PEM cert into the server app"
cp "$certs_dir/client-cert.pem" "$server_dir/src/main/resources/"

echo "Installing server PEM cert into the server app"
cp "$certs_dir/server-cert.pem" "$server_dir/src/main/resources/"

echo "Installing server key into the server app"
cp "$certs_dir/server-key-unencrypted.pem" "$server_dir/src/main/resources/"
