
all: run

build:
	mvn clean validate install
run: build
	mvn -f pom.xml spring-boot:run
clean:
	mvn -DskipTests=true clean
