check-updates:
	./gradlew dependencyUpdates

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

run:
	./gradlew run

report:
	./gradlew jacocoTestReport

build: lint test
	./gradlew clean build

install:
	./gradlew install

.PHONY: build