FROM maven:3.9.9-eclipse-temurin-17-focal AS build
WORKDIR /home/app
COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/uax/madm/devops/campaigns_demo/CampaignsDemoApplication.java /home/app/src/main/java/uax/madm/devops/orders_demo/CampaignsDemoApplication.java

RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:17.0.14_7-jdk-ubi9-minimal
VOLUME /tmp
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh","-c", "java -jar /app.jar"]