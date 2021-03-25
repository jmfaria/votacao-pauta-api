mvn clean package &&
docker volume create --name=mongodbdata &&
docker-compose up -d --build