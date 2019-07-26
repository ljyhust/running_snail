FROM java:8
VOLUME /tmp
ADD manage_registry-0.0.1-SNAPSHOT.jar manage_registry-v1.jar
CMD ["java", "-Xmx200m", "-jar", "manage_registry-v1.jar"]

EXPOSE 10010