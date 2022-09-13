package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public boolean updateById(Long id, Recipe newRecipe) {
        Recipe recipe = findById(id);
        if (recipe != null) {
            recipe.setName(newRecipe.getName());
            recipe.setCategory(newRecipe.getCategory());
            recipe.setDescription(newRecipe.getDescription());

            var ingredients = recipe.getIngredients();
            var directions = recipe.getDirections();
            ingredients.clear();
            directions.clear();
            ingredients.addAll(newRecipe.getIngredients());
            directions.addAll(newRecipe.getDirections());
            save(recipe);

            return true;
        }

        return false;
    }

    public boolean deleteById(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> searchByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
}
