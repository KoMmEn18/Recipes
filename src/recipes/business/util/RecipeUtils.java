package recipes.business.util;

import recipes.business.Recipe;
import recipes.business.RecipeDirection;
import recipes.business.RecipeIngredient;
import recipes.business.dto.RecipeDTO;

import java.util.stream.Collectors;

public class RecipeUtils {

    public static Recipe convertToEntity(RecipeDTO recipeDTO) {
        return new Recipe(
                recipeDTO.getName(),
                recipeDTO.getDescription(),
                recipeDTO.getIngredients().stream()
                        .map(RecipeIngredient::new)
                        .collect(Collectors.toList()),
                recipeDTO.getDirections().stream()
                        .map(RecipeDirection::new)
                        .collect(Collectors.toList())
        );
    }

    public static RecipeDTO convertToDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getName(),
                recipe.getDescription(),
                recipe.getIngredients().stream().map(RecipeIngredient::getName).collect(Collectors.toList()),
                recipe.getDirections().stream().map(RecipeDirection::getDescription).collect(Collectors.toList())
        );
    }
}
