FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app/discovery-service

COPY ./pom.xml /app
COPY ./discovery-service/.mvn ./.mvn
COPY ./discovery-service/mvnw .
COPY ./discovery-service/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./discovery-service/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "discovery-service-0.0.1-SNAPSHOT.jar"]
