trap killall SIGINT

killall(){
  echo "Killing containers..."
  docker kill finance_db
  docker kill finance_mq

  echo "Removing containers..."
  docker rm finance_db
  docker rm finance_mq

  echo "Killing other processes..."
  kill 0
}

checkhealth(){
  [ "$(docker ps -a | grep $1)" ] && [[ $(docker inspect --format "{{.State.Health.Status}}" $1) == "healthy" ]]
  return 
}

export SPRING_DATASOURCE_URL=jdbc:mysql://127.0.0.1:5000/ies
export SPRING_DATASOURCE_USERNAME=ies
export SPRING_DATASOURCE_PASSWORD=password
export FINANCE_RABBITMQ_HOST=127.0.0.1

docker run --name finance_db --health-cmd='mysqladmin ping' -e MYSQL_DATABASE=ies -e MYSQL_USER=ies -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -p 5000:3306 mysql:8.0.27 &
docker run --name finance_mq --health-cmd='rabbitmq-diagnostics -q status' -p 5672:5672 rabbitmq:3.9-alpine &

sleep 1

until checkhealth finance_mq; do sleep 1; done;

cd ./projDataGenerator
python3 main.py &

until checkhealth finance_db; do sleep 1; done;


cd ../projBackend/finance
./mvnw spring-boot:run &

cd ../../projFrontend
ng serve &

wait