FROM openjdk:8

WORKDIR /app

COPY exam-serve-*.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar"]

CMD ["exam-server-*.jar"]