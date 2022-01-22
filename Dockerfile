FROM openjdk:8 as build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -

COPY src src

COPY src/main/resources/application.docker.yml src/main/resources/application.yml

RUN  mvn -DskipTests package

FROM openjdk:8 as release

EXPOSE 8889

COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
