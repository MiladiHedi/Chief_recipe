# simple_java_webApp

Its a simple java web app example, a recipe app.
In this app you can show and add/update recipes with ingredients and steps description

This  app use spring boot, java 8, maven,mysql, thymeleaf, docker, docker compose, nginx

## How deploy locally

First run compile and generate war with maven
./mvnw clean package
(clean if you have already run "mvn package")

Then run the docker stack
docker-compose up -d

## verify in your browser

app1 address : http://localhost:8081/recipeWebApp/
app2 address : http://localhost:8082/recipeWebApp/

load balencer address : http://localhost:8083/recipeWebApp/

On the top of each web page ( on top of "Famous recipes") you can see the name of the app replicas, when you refresh the load balencer address, app number change.
