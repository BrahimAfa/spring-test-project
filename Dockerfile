FROM openjdk:8
EXPOSE 8888
ADD target/docker-jeckin-implementation-attsw.jar docker-jeckin-implementation-attsw.jar
ENTRYPOINT ["java","-jar", "/docker-jeckin-implementation-attsw.jar"]
