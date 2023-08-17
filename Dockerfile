FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser

COPY target/Monitoring_API-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R javauser:javauser .

USER javauser

ENTRYPOINT ["java", "-jar", "app.jar"]