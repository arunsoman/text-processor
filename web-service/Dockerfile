FROM unocha/alpine-jdk:8

COPY target/tp*.jar tp.jar
COPY extractjar.sh extractjar.sh
RUN chmod 777 ./extractjar.sh
RUN ./extractjar.sh
ENTRYPOINT java  -jar tp.jar