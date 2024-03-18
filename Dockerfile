FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/account-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} compose-account.jar
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "compose-account.jar"]