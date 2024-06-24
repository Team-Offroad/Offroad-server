FROM amd64/amazoncorretto:21
WORKDIR /app
COPY ./build/libs/offloadserver-0.0.1-SNAPSHOT.jar /app/offroad.jar
CMD ["sh", "-c", "java -Duser.timezone=Asia/Seoul -jar -Dspring.profiles.active=dev offroad.jar > 2>&1"]