FROM openjdk:11
ADD ./target/userservice-0.0.1-SNAPSHOT.jar /usr/src/userservice-0.0.1-SNAPSHOT.jar
EXPOSE 8080
WORKDIR /usr/src
ENTRYPOINT ["java","-jar","userservice-0.0.1-SNAPSHOT.jar"]

