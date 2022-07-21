# Whatsapp API Demo

A simple Whatsapp like service 

- [Setup](#setup)
    - [Prerequisites](#prerequisites)
    - [Repos](#repos)
    - [Database](#database)
- [Running the application](#running-the-application)
- [Testing the application](#testing-the-application)
- [OpenApi documentation](#openapi-documentation)

## Setup

### Prerequisites

If not already installed, install the following prerequisites:

* Java SE 11 (JDK) - Java first! Maven will refuse to install otherwise. Use `apt-get install openjdk-11-jdk` or
  similar.
* Maven - Use `apt-get install maven` or similar.
* PostgreSQL client - Use `apt-get install postgresql-client` or similar.
* A push notification service - `utils/reflect.py` is provided to simulate this

### Repos

Clone `whatsapp-api-demo` and `cd` into it.

### Database

Simply use docker and run the PostgreSQL image with customized port for the local instance:

    docker run --name ns-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:11-alpine

Create the database, you will be prompted for the password:

    echo "CREATE DATABASE whatsappdb;" | psql -h localhost -U postgres

For future access just use `docker start ns-postgres` to start the container.

## Running the application

1. From the base directory, type:

        mvn spring-boot:run

   or:

       java \
         -jar target/<NETWORK-SERVICE.WAR>


   The base config is set in `src/main/resources/application.yaml`.


>

1. Use Postman or terminal to send requests to endpoints.

## Testing the application

How do I run tests?

1. `mvn test` -> for unit tests only


1. `mvn verify` -> all tests


## OpenApi Documentation

You can find limited OpenApi documentation at `/swagger-ui/index.html?configUrl=/api-docs/swagger-config`