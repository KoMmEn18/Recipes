package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.Recipe;
import recipes.business.RecipeService;
import recipes.business.User;
import recipes.business.dto.RecipeDTO;
import recipes.business.util.RecipeUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    private RecipeService recipeService;
    private Map<String, Function<String, List<Recipe>>> searchHandler = new HashMap<>();

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
        searchHandler.put("name", recipeService::searchByName);
        searchHandler.put("category", recipeService::searchByCategory);
    }

    @GetMapping("/recipe/{id}")
    public RecipeDTO getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findById(id);

        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe of given id not found");
        }

        return RecipeUtils.convertToDTO(recipe);
    }

    @PostMapping("/recipe/new")
    public Map<String, Long> postRecipe(@Validated @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.save(RecipeUtils.convertToEntity(recipeDTO));

        return Collections.singletonMap("id", recipe.getId());
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<String> updateRecipe(
            @PathVariable long id,
            @Validated @RequestBody RecipeDTO recipeDTO,
            @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe of given id not found");
        }

        if (recipe.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not permitted to update a recipe that does not belong to you.");
        }

        recipeService.update(recipe, RecipeUtils.convertToEntity(recipeDTO));
        return new ResponseEntity<>("Recipe with id = " + id + " have been updated", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe of given id not found");
        }

        if (recipe.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not permitted to delete a recipe that does not belong to you.");
        }

        recipeService.delete(recipe);
        return new ResponseEntity<>("Recipe with id = " + id + " have been deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recipe/search")
    public List<RecipeDTO> searchRecipe(@RequestParam Map<String, String> params) {
        Map.Entry<String, String> paramEntry;
        if (params.size() != 1 || !searchHandler.containsKey((paramEntry = params.entrySet().iterator().next()).getKey())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You should specify exactly one parameter from {name, category}");
        }

        return searchHandler.get(paramEntry.getKey())
                .apply(paramEntry.getValue())
                .stream()
                .map(RecipeUtils::convertToDTO)
                .collect(Collectors.toList());
    }
}
