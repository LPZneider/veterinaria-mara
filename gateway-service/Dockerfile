FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app/gateway-service

COPY ./pom.xml /app
COPY ./gateway-service/.mvn ./.mvn
COPY ./gateway-service/mvnw .
COPY ./gateway-service/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./gateway-service/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/gateway-service/target/gateway-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "gateway-service-0.0.1-SNAPSHOT.jar"]
