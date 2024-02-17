FROM eclipse-temurin:17 as jre-build

# Workflow to test
# jlink --module-path $JAVA_HOME/jmods --add-modules java.base,java.logging,java.prefs,java.desktop,java.management,java.naming,java.security.jgss,java.instrument --output ./mycustomrt
# ./mycustomrt/bin/java -jar target/spring-boot-ssl-bundles-0.0.1-SNAPSHOT.jar

RUN $JAVA_HOME/bin/jlink \
  --add-modules java.base,java.logging,java.prefs,java.desktop,java.management,java.naming,java.security.jgss,java.instrument \
  --strip-debug \
  --no-man-pages \
  --no-header-files \
  --compress=2 \
  --output /javaruntime

# Define your base image
FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
ENV JAVA_OPTS="-Xmx1024m -Xms256m "
COPY --from=jre-build /javaruntime $JAVA_HOME

# Continue with your application deployment
RUN mkdir /opt/app
ARG JAR_FILE=target/spring-boot-*.jar
COPY ${JAR_FILE} /opt/app/japp.jar
# COPY --from=jre-build ${JAR_FILE} /opt/app/japp.jar
CMD ["java", "-jar", "/opt/app/japp.jar", "-classpath", "/massl"]

