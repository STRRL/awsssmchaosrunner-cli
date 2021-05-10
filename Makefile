.PHONY: build run

all: clean build

build:
	./gradlew build

clean:
	./gradlew clean