# Etapa 1: Construção do projeto
FROM maven:3.8.4-eclipse-temurin-17 AS build

# Altera o diretório para o /app dentro do container
WORKDIR /app

# Instala o tzdata e configura o fuso horário
RUN apt-get update && apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

ENV TZ=America/Sao_Paulo

# Copia os arquivos do projeto para dentro do container
COPY pom.xml .
COPY src ./src

# Executa o Maven para compilar o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para rodar a aplicação
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/backend-1.0.0-SNAPSHOT.jar app.jar

# Expõe a porta 8084
EXPOSE 8084

# Comando para rodar a aplicação
CMD ["java", "-Duser.timezone=America/Sao_Paulo", "-jar", "app.jar"]
