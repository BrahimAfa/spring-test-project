FROM openjdk:8 as build
ENV DB_HOST=localhost
# ADDING postgress repositories apt source list
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ bullseye-pgdg main" >> /etc/apt/sources.list.d/pgdg.list
RUN wget -q https://www.postgresql.org/media/keys/ACCC4CF8.asc -O - | apt-key add -

# installing the necessary packages for postgress to run
RUN apt-get update && apt-get install -y software-properties-common postgresql-9.3 postgresql-client-9.3 postgresql-contrib-9.3 apt maven

# using postgress user to execute psql commands
USER postgres

# initialazing attsw database with the user and password
RUN    /etc/init.d/postgresql start &&\
    psql --command "CREATE USER postgress WITH SUPERUSER PASSWORD 'postgress';" &&\
    createdb -O postgress attsw

# configuring the postgress to run on all interfaces
RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/9.3/main/pg_hba.conf
RUN echo "listen_addresses='*'" >> /etc/postgresql/9.3/main/postgresql.conf

# switching to the root user to install the app packages and do the tests
USER root
WORKDIR /app
COPY pom.xml .
# installing dependincies so the app can run
RUN mvn dependency:go-offline -B
COPY src src
COPY src/main/resources/application.docker.yml src/main/resources/application.yml

# running the postgress server and passing the test
RUN runuser -l postgres -c '/usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/9.3/main -c config_file=/etc/postgresql/9.3/main/postgresql.conf'&  mvn package test

# at this stage everything is good if the test passes that means that
# we are ready to copy the jar files and run them
# and that is what we are doing in the realeas part

FROM openjdk:8 as release
EXPOSE 8889
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]

## the following two lines are not recommened
# TO RUN THIS IMAGE WITH POSTGRESS YOU HAVE TO UNCOMMENT THE FOLLOWING TWO LINES
#RUN cp /usr/lib/postgresql/9.3/bin/postgres /usr/bin/postgres
#CMD runuser -l postgres -c 'postgres -D/var/lib/postgresql/9.3/main -cconfig_file=/etc/postgresql/9.3/main/postgresql.conf'& java -jar /app/target/*.jar
