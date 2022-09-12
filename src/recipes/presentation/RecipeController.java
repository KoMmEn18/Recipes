package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.Recipe;
import recipes.business.RecipeService;
import recipes.business.dto.RecipeDTO;
import recipes.business.util.RecipeUtils;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe/new")
    public Map<String, Long> postRecipe(@Validated @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.save(RecipeUtils.convertToEntity(recipeDTO));

        return Collections.singletonMap("id", recipe.getId());
    }

    @GetMapping("/recipe/{id}")
    public RecipeDTO getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findById(id);

        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe of given id not found");
        }

        return RecipeUtils.convertToDTO(recipe);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        if (recipeService.deleteById(id)) {
            return new ResponseEntity<>("Recipe with id = " + id + " have been deleted", HttpStatus.NO_CONTENT);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe of given id not found");
    }
}
