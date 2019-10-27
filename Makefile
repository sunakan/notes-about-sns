config:
	docker-compose config

build:
	docker-compose build

up:
	docker-compose up -d

down:
	docker-compose down

ash:
	docker-compose exec sns ash
bash:
	docker-compose exec java bash

test:
	docker-compose exec -u gradle java ./gradlew test --rerun-tasks
	docker-compose exec sns cat /tmp/sns.log

topics:
	aws sns --endpoint-url http://localhost:9911 list-topics

publish:
	aws sns --endpoint-url http://localhost:9911 publish --topic-arn arn:aws:sns:ap-northeast-1:1465414804035:test1 --message "HELLO`date`"
	docker-compose exec sns cat /tmp/sns.log
