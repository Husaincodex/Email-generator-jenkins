FROM amazoncorretto:21-alpine

WORKDIR /app

COPY src/ ./src/

RUN mkdir -p out && \
    javac -d out src/emailgenerator/*.java

CMD ["java", "-cp", "out", "emailgenerator.EmailMainProgram"]
