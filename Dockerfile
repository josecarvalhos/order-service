# Etapa 1: Construir a aplicação
FROM maven:3.9.4-eclipse-temurin-21 as builder

# Diretório de trabalho para a construção
WORKDIR /app

# Copiar arquivos necessários para o build
COPY pom.xml .
COPY src ./src

# Instalar dependências e compilar o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Imagem de execução
FROM eclipse-temurin:21-jre

# Diretório de trabalho no contêiner
WORKDIR /app

# Copiar o JAR gerado na etapa anterior
COPY --from=builder /app/target/order.jar order.jar

# Expor a porta do Spring Boot
EXPOSE 8080

# Executar o JAR
ENTRYPOINT ["java", "-jar", "order.jar"]
