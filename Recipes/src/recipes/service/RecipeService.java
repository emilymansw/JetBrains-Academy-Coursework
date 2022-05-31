package recipes.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import recipes.entities.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).isPresent() ? recipeRepository.findById(id).get() : null;
    }

    public List<Recipe> searchRecipeContainingName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> searchRecipeByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    public boolean isExisted(Long id) {
        return recipeRepository.existsById(id);
    }

}
