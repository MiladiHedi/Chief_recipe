Chief recipe
=============
[![Build Status](https://travis-ci.org/MiladiHedi/Chief_recipe.svg?branch=master)](https://travis-ci.org/MiladiHedi/Chief_recipe)

With" Chief recipe" you can display recipes with ingredients and steps description.

it's a basic java web app, developped in Java, spring boot and others springs frameworks, and  use maven, mysql, thymeleaf, docker, docker compose, nginx 

This app is based from https://github.com/springframeworkguru/spring5-recipe-app, I followed his training to get up to date with java and spring frameworks skills. I implemented the application in my way and I completed it.


## How deploy locally

First run compile and generate war with maven
```
./mvnw clean package
```
(clean if you have already run "./mvnw package")

Then run the docker stack
```
docker-compose up db
```
At the fisrt time, wait for complete initialisation of database and then run 
```
docker-compose up -d
```
## Use it

You can check these url address :
* app1 (replicas1) : http://localhost:8081/recipeWebApp/
* app2 (replicas2) : http://localhost:8082/recipeWebApp/
* load balencer : http://localhost:8083/recipeWebApp/

On the top of each web page ( on top of panel "Famous recipes") you can see the name of the app replicas, when you refresh the load balencer address, app number change.
