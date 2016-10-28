# Dockerfile
FROM anapsix/alpine-java

MAINTAINER  Arun soman <author@email.com>

RUN apk add git && \
   apk add maven && \
   git clone https://github.com/arunsoman/text-processor.git && \
   cd text-processor && mvn package -Dmaven.test.skip=true && \
   cd .. && \
   cp text-processor/compiler/target/compiler.jar . && \
   rm -rf text-processor && \
   apk del git && \
   apk del maven


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/compiler.jar"]
