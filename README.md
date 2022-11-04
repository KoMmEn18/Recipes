# Recipes
Simple REST API app to store recipes created by users.

## Technologies
- Java
- Spring Boot among with security component
- JSON as data transfer
- H2 database
- JPA

## Endpoints
- *POST* **/api/register** <br>
receives a JSON object with two fields: email (string), and password (string) <br>
```json
{
   "email": "testemail@test.com",
   "password": "SgmaS23d"
}
```

- *GET* **/api/recipe/{id}** <br>
returns a recipe with a specified id as a JSON object (where {id} is the id of a recipe)
- *POST* **/api/recipe/new** <br>
receives a recipe as a JSON object and returns a JSON object with one id field. This is a uniquely generated number by which we can identify and retrieve a recipe later.<br>
```json
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

- *PUT* **/api/recipe/{id}** <br>
receives a recipe as a JSON object and updates a recipe with a specified id
```json
{
   "name": "Fresh Mint Tea",
   "category": "beverage-new",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-7 minutes", "Add honey and sugar and mix again"]
}
```
- *DELETE* **/api/recipe/{id}** <br>
deletes a recipe with a specified id
- *GET* **/api/recipe/search** <br>
takes one of the two mutually exclusive query parameters:
  - category – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
  - name – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).
