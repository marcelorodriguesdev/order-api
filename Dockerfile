FROM openjdk:17

WORKDIR /app

COPY ./target/order-0.0.1-SNAPSHOT.jar order-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT java -jar order-0.0.1-SNAPSHOT.jar
