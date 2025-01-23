FROM openjdk:17-slim  as build
WORKDIR /app
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src
RUN ./gradlew build -x test

FROM bellsoft/liberica-openjre-alpine-musl:17@sha256:0b72e9b4f5d647ec967a6e7066c0326637694d67022edad2f33ee5de659a218c
ARG DEPENDENCY=/app/build
COPY --from=build ${DEPENDENCY}/libs/*.jar app/lib/app.jar
ENTRYPOINT ["java","-jar","app/lib/app.jar"]