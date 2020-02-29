FROM openjdk:8-jre-alpine
ADD entrypoint.sh entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
EXPOSE 8080
ADD /target/*.jar app.jar