#! /bin/sh
make build
java -jar build/libs/awsssmchaosrunner-cli-fat-1.0-SNAPSHOT.jar "$@"