package recipes.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    private List<String> ingredients;

    @NotEmpty
    private List<String> directions;
}
