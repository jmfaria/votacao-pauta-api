call mvn clean package
call docker volume create --name=mongodbdata
call docker-compose up -d --build