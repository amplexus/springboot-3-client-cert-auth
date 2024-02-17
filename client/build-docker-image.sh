#!/usr/bin/env bash

# jlink --module-path $JAVA_HOME/jmods --add-modules java.base,java.logging,java.prefs,java.desktop,java.management,java.naming,java.security.jgss,java.instrument --output mycustomrt
# ./mycustomrt/bin/java -jar target/spring-boot-ssl-bundles-0.0.1-SNAPSHOT.jar

docker build -t springboot-sslbundle-client:1.0 .
