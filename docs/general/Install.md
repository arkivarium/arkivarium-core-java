# INSTALL

This document is structured in way to provide you with options on how to get the application up and running. If you want
to get an overview of all options then you might want to read this document in full. We currently have 3 ways to run the
application. These are _maven_, _make_ and _docker_. If you want to use something else, please update this document.

Nikita is a Spring boot application follows Spring boot releases, and as such, inherits some requirements from Spring
Boot. Nikita is developed on a Linux machine with Apache Maven 3.6.3 and Java 17. Java 17 is a minimum requirement to
build the code. Please make sure both of these are installed before you attempt to run the project. You can verify your
versions with:

    mvn --version
    java -version

## Getting the code

The latest version of the code is available from Gitlab at
[OsloMet-ABI/nikita-noark5-core](https://gitlab.com/OsloMet-ABI/nikita-noark5-core).
If you haven't cloned the project then:

    git clone https://gitlab.com/OsloMet-ABI/nikita-noark5-core.git

If you already have the code consider synchronizing your local copy:

    git fetch --all
    git checkout origin/master

See [test data](#populating-the-database) to understand how you can populate the core with some example data.

## Keycloak requirement

We currently follow Spring Boot recommendation for SSO and now require a running keycloak alongside nikita. The easiest
way to get keycloak running is to use docker

    docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.1.2 start-dev

Once you have keycloak running you can, from the root of the nikita source code run:

    scripts/populate_keycloak.sh

to create users and roles, so you can interact with nikita.

## Database and Elasticsearch Configuration

The default profile creates an in-memory database using H2 and connects to an Elasticsearch
instance running on localhost:9200 without any username or password. MariaDB or Postgres can be
used instead of H2 by activating the corresponding Spring profile ("mariadb" or "postgres"). Under
Spring Boot 3, if Elasticsearch is not running, nikita will start but there will be periodic
warnings in the log file about not being able to connect to Elasticsearch.

The following environment variables can be set to further configure the data connections:

| Variable Name       |                                      Comment                                       |                                                                                                                                  Default |
|---------------------|:----------------------------------------------------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------:|
| NIKITA_BASE_DIR     |          The root directory used for storage of document files in Nikita.          |                                                                                                                                 `/data2` |
| DB_URI              | The JDBC string for connecting to the DB when using the mysql / postgres profiles. | Mysql: `jdbc:mariadb://localhost:3306/nikita_noark5_prod?serverTimezone=Europe/Oslo` Postgres: `jdbc:postgresql://localhost:5432/nikita` |
| DB_USER             |                                    The DB user.                                    |                                                                                         `INSERT-USERNAME-HERE` (or blank when using H2). |
| DB_PASS             |                             The password for DB_USER.                              |                                                                                         `INSERT-PASSWORD-HERE` (or blank when using H2). |
| ELASTIC_URI         |                              URI to the Elastic host                               |                                                                                                                 `http://localhost:8200`. |
| ELASTIC_USER        |                                 The elastic user.                                  |                                                                                                      No default (i.e. no auth required). |
| ELASTIC_PASS        |                             The elastic user password.                             |                                                                                                                              No default. |
| ELASTIC_PASS        |                             The elastic user password.                             |                                                                                                                              No default. |
| KEYCLOAK_ISSUER_URI |                              URI issuer for keycloak                               |                                                                                               http://localhost:8080/realms/recordkeeping |

## Makefile

This option is a wrapper around the maven command.

To compile the core and start it automatically, from the top level directory:

    make

## Maven

Please note that maven will automatically download all dependencies (jar files)
and put them in a directory ~/.m2. If you are uncomfortable with this, please check the pom.xml files to find out which
jar files will be downloaded.

    mvn clean validate install
    mvn spring-boot:run
    # Or, using an alternative ELASIC_URI:
    # mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DELASTIC_URI=http://elasticsearch:9200"
    # Or, using an alternative Spring profile and a custom DB username and password:
    # mvn spring-boot:run -Dspring-boot.run.profiles=mysql -Dspring-boot.run.jvmArguments="-DDB_USER=mydbuser -DDB_PASS=mypass"

The debian operating system provides some packages that can install some of these packages for you. If you have your
maven repository you can set it accordingly in pom.xml.

You will see a lot of different startup messages, but there should be no exceptions. (Please let us know if there are
any exceptions).

The program should output something like the following if everything is successful

 	Application 'OsloMet Noark 5 Core (Demo mode)' is running! Access URLs:
 	Local: 			http://localhost:8092
 	External: 		http://127.0.1.1:8092
 	contextPath: 	http://127.0.1.1:8092/noark5v5
 	Application is running with following profile(s): []

## Local Docker image via maven

Spring Boot allows you to package nikita into a docker image. Just run

     mvn spring-boot:build-image

in the root of the project directory and it should build a docker image for you. The output of the above process could
be something like

    [INFO] Successfully built image 'docker.io/library/nikita-noark5-core:0.7'

To run the image use:

     docker run --network=host -dit -v /data2:/data2  docker.io/library/nikita-noark5-core:0.7

Note. Make sure the /data2 directory exists or provide another location that docker can use as a file storage

## Populating the database

The easiest way of populating nikita (the database) with data is to
use [noark5-tester](https://codeberg.org/noark/noark5-tester).
Using noark5-tester you should be able to create a nearly complete Noark 5 setup using the command:

    python3 ./runtest --keep

Other tools in that project can create extractions from nikita.