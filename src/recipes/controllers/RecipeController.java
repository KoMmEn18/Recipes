package recipes.controllers;

import org.springframework.web.bind.annotation.*;
import recipes.models.Recipe;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    private Recipe recipe;

    @PostMapping("/recipe")
    public void postAddress(@RequestBody Recipe recipe) {
        this.recipe = recipe;
    }

    @GetMapping("/recipe")
    public Recipe getRecipe() {
        return recipe;
    }
}
