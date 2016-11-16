FROM unocha/alpine-jdk:8

COPY target/tp*.jar /tp.jar
COPY extractjar.sh ./extractjar.sh
RUN chmod 777 ./extractjar.sh
RUN ./extractjar.sh
ENTRYPOINT java  -cp '/tp.jar:/BOOT-INF/lib/*' org.springframework.boot.loader.JarLauncher