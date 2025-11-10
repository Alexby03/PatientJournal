FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /build/target/quarkus-app/lib/ ./lib/
COPY --from=build /build/target/quarkus-app/*.jar ./
COPY --from=build /build/target/quarkus-app/app/ ./app/
COPY --from=build /build/target/quarkus-app/quarkus/ ./quarkus/

EXPOSE 8080

# Använd host.docker.internal för att nå host-datorn
ENV QUARKUS_DATASOURCE_REACTIVE_URL=mysql://host.docker.internal:3306/patientjournaldb
ENV QUARKUS_DATASOURCE_USERNAME=root
ENV QUARKUS_DATASOURCE_PASSWORD=admin

CMD ["java", "-jar", "quarkus-run.jar"]
