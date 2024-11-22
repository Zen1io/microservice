# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS builder

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код проекта и собираем jar-файл
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk

# Устанавливаем рабочую директорию для контейнера
WORKDIR /app

# Копируем jar-файл из предыдущего этапа
COPY --from=builder /app/target/MyEighthProject-0.0.1-SNAPSHOT.jar app.jar

# Указываем порт, который будет использовать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
