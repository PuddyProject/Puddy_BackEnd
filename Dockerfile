FROM openjdk:17-jdk
ENV  S3_BUCKET=puddybucket
ENV S3_REGION=ap-northeast-2
ENV S3_ACCESS=LIsalrJkJmIRDndkvTHs
ENV S3_SECRET=eyFgNNu4IgN5x7mG4eKyvVzdvn5oNPRKsITgRobP
ENV DB_URL=jdbc:mysql://34.64.162.186:3306/puddy
ENV DB_PASSWORD=0913
ENV JWT_SECRET=104f205379c2ff5cdfa3a61a1b5a860193072bbeebf6f90356595a81d6b752a2
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
