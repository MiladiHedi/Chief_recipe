FROM maven:3.5.2-jdk-8 AS build
COPY src /usr/src/recipe_app/src  
COPY pom.xml /usr/src/recipe_app  
RUN mvn -f /usr/src/recipe_app/pom.xml clean package

FROM openjdk:8
COPY --from=build /usr/src/recipe_app/target/recipeWebApp.jar /usr/recipe_app/recipeWebApp.jar 
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/recipe_app/recipeWebApp.jar"]  