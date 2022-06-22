check-updates:
	./gradlew dependencyUpdates

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

run:
	./gradlew run

start:
	APP_ENV=development ./gradlew run

start-dist:
	APP_ENV=production ./build/install/app/bin/app

report:
	./gradlew jacocoTestReport

build: lint test
	./gradlew clean build

install:
	./gradlew clean install

generate-migrations:
	./gradlew generateMigrations

.PHONY: build