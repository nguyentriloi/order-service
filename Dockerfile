FROM amazoncorretto:17-al2023

ADD target/app.jar /app.jar

ENTRYPOINT java -jar /app.jar

