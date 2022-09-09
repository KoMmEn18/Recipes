package recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.Recipe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    private final ConcurrentMap<Integer, Recipe> recipes = new ConcurrentHashMap<>();

    @PostMapping("/recipe/new")
    public Map<String, Integer> postRecipe(@RequestBody Recipe recipe) {
        int id = recipes.size() + 1;
        recipes.put(id, recipe);

        return Collections.singletonMap("id", id);
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        Recipe result = recipes.get(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe of given id not found");
        }

        return result;
    }
}
