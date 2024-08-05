# Define a imagem base
FROM openjdk:19-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR da aplicação para o diretório de trabalho
COPY target/task-management-0.0.1-SNAPSHOT.jar task-management.jar

# Define o comando para executar a aplicação quando o container iniciar
CMD ["java", "-jar", "task-management.jar"]
