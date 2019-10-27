config:
	docker-compose config

build:
	docker-compose build

up:
	docker-compose up -d

down:
	docker-compose down

bash:
	docker-compose exec -u gradle java bash

test:
	docker-compose exec -u gradle java ./gradlew test
