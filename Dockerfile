# Use a imagem oficial do OpenJDK 21 como base
FROM maven:3.9.2-eclipse-temurin-17-alpine as builder

# Defina o diretório de trabalho dentro do contêiner
COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package -DskipTests

# Copie o arquivo JAR da sua aplicação para dentro do contêiner
FROM eclipse-temurin:17-jre-alpine
COPY --from=builder target/*.jar task-management.jar
EXPOSE 8080

# Defina a variável de ambiente SPRING_PROFILES_ACTIVE para 'prod'
#ENV spring.profiles.active=prod

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "task-management.jar"]


