FROM sonaky47/payhere-homework
WORKDIR /app
COPY . /app
ENV LC_ALL=C.UTF-8
RUN whoami
RUN locale
RUN java -version
RUN ./gradlew :payhere-api:clean
RUN ./gradlew :payhere-api:build
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "./payhere-api/build/libs/payhere-api-0.0.1-SNAPSHOT.jar"]
