FROM adoptopenjdk/openjdk11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/knapsack-0.0.1-SNAPSHOT.jar knapsack.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","knapsack.jar"]