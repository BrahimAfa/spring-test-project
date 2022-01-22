FROM openjdk:8 as build

RUN apt-get update && apt-get install -y software-properties-common maven

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src src

COPY src/main/resources/application.docker.yml src/main/resources/application.yml

RUN mvn package -DskipTests

FROM openjdk:8 as release

EXPOSE 8889

COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
