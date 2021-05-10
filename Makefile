.PHONY: build run image

all: clean build

build:
	./gradlew build

clean:
	./gradlew clean

image: all
	docker build \
		-t strrl/awsssmchaosrunner-cli:latest \
		-t strrl/awsssmchaosrunner-cli:1.0-SNAPTSHOT \
		.
