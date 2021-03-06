check-updates:
	./gradlew dependencyUpdates

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

startdev:
	APP_ENV=development ./gradlew run

startprod:
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