FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app/auth-service

COPY ./pom.xml /app
COPY ./auth-service/.mvn ./.mvn
COPY ./auth-service/mvnw .
COPY ./auth-service/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./auth-service/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/auth-service/target/auth-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

ENTRYPOINT ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]
