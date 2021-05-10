FROM java:8
COPY ./build /app/build
COPY ./runner.sh /app/runner.sh
WORKDIR /app
ENTRYPOINT /app/runner.sh