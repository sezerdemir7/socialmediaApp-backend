FROM openjdk:17


COPY socialApp.jar socialapp.jar

ENTRYPOINT ["java","-jar","/socialapp.jar"]