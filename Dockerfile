# Java21 de amazon AWS
FROM amazoncorretto:21

RUN yum install -y iputils
RUN yum install -y procps

# Argumento de entrada por comando
ARG JAR_FILE

# Situado en el directorio raiz
WORKDIR /

# Copia el archivo .jar especificado al contenedor
COPY ${JAR_FILE} app.jar

# Permisos ejecucion
RUN chmod +x app.jar

# Ejecuta app jar
CMD ["java", "-jar", "/app.jar", ">>", "/var/log/app.log", "2>&1"]
