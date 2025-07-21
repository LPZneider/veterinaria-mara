FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app/owner-service

COPY ./pom.xml /app
COPY ./owner-service/.mvn ./.mvn
COPY ./owner-service/mvnw .
COPY ./owner-service/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./owner-service/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/owner-service/target/owner-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8002

ENTRYPOINT ["java", "-jar", "owner-service-0.0.1-SNAPSHOT.jar"]
