FROM maven:3-jdk-8
VOLUME /tmp

ENV SRC_DIR "/srv/nikita-noark5-core"
ENV BRANCH "master"

# Get the source
COPY . $SRC_DIR
RUN git -C $SRC_DIR checkout $BRANCH

# Build the application
RUN mvn -f $SRC_DIR/pom.xml -Dmaven.test.skip=true clean install
RUN mvn -f $SRC_DIR/pom.xml -Dmaven.test.skip=true package spring-boot:repackage
RUN cp $SRC_DIR/target/nikita-noark5-core-0.1.0.jar app.jar

# Make the jar available
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8092 8082
