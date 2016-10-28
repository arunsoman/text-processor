# Dockerfile
FROM anapsix/alpine-java

MAINTAINER  Arun soman <author@email.com>

RUN echo 'deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main' >> /etc/apt/sources.list && \
    echo 'deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main' >> /etc/apt/sources.list && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys C2518248EEA14886 && \
    apt-get update && \
    apt-get -y install maven && \
apt-get -y install git-all && \
git clone https://github.com/arunsoman/text-processor.git && \
cd text-processor && mvn package -Dmaven.test.skip=true && \
cp compiler/target/compiler.jar ../ && \
rm -rf ../text-processor && \
apt-get purge -y --auto-remove maven && \
apt-get purge -y --auto-remove git-all && \
apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/compiler.jar"]
