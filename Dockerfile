# STEP 1 - BUILD JAVA APPLICATION USING MAVEN
FROM maven:3.6.0-jdk-11-slim AS builder

WORKDIR /home/app/

COPY ./pom.xml ./pom.xml
COPY ./settings.xml ./settings.xml

RUN mvn -s ./settings.xml dependency:go-offline -B 

COPY ./src ./src

RUN mvn -s ./settings.xml package -DskipTests=true

# STEP 2 - SERVE JAVA APPLICATION USING JRE
FROM java:8

COPY --from=builder /home/app/target/template-1.0.jar /usr/local/lib/template-service.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/usr/local/lib/template-service.jar" ]
