FROM maven:3.5.3-jdk-8
ENV APP_DIR=/app
RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR
COPY pom.xml .
RUN mvn -B dependency:resolve -Dclassifier=test
COPY . $APP_DIR
RUN mvn -B test-compile compile
ENTRYPOINT ["mvn", "test"]
