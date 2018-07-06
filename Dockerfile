FROM maven:3-jdk-8

# Selectively add stuff we need
COPY pom.xml /clicktrips-automation/

# Get a clean build after
RUN cd /clicktrips-automation && mvn dependency:go-offline
COPY src /clicktrips-automation/src
WORKDIR /clicktrips-automation/

# Additional files that are needed
COPY . /clicktrips-automation

CMD [ "mvn test" ]