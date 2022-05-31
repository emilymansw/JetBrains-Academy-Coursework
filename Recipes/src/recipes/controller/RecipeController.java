package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;
import recipes.entities.User;
import recipes.repository.UserRepository;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable("id") @Min(0) long id){
        Recipe recipeFound = recipeService.findRecipeById(id);
        if(recipeFound == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe not found.");
        } else {
            return recipeFound;
        }
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> saveReceipt(@Valid @RequestBody Recipe recipe){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof User) {
            email = ((User)principal).getUsername();
        } else {
            email = principal.toString();
        }
        recipe.setUser(userRepo.findByEmail(email));
        Recipe recipeCreate = recipeService.save(recipe);
        return Map.of("id", recipeCreate.getId());
    }

    @PutMapping ("/api/recipe/{id}")
    public void updateReceipt(@Valid @RequestBody Recipe recipe, @PathVariable("id") @Min(1) long id){
        if (!recipeService.isExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe not found.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Recipe updateRecipe = recipeService.findRecipeById(id);
        if(user.getId() != updateRecipe.getUser().getId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "FORBIDDEN");
        }
        updateRecipe.setDate(LocalDateTime.now());
        updateRecipe.setName(recipe.getName());
        updateRecipe.setCategory(recipe.getCategory());
        updateRecipe.setDescription(recipe.getDescription());
        updateRecipe.setDirections(recipe.getDirections());
        updateRecipe.setIngredients(recipe.getIngredients());
        recipeService.save(updateRecipe);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                "Recipe deleted.");
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable("id") @Min(1) long id){
        if (!recipeService.isExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe not found.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Recipe deleteRecipe = recipeService.findRecipeById(id);
        if(user.getId() != deleteRecipe.getUser().getId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "FORBIDDEN");
        }
        recipeService.delete(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                "Recipe deleted.");
    }

    @GetMapping(value = "/api/recipe/search", params = "name")
    public List<Recipe> searchRecipeByName(@RequestParam String name){
        List<Recipe> recipeFound = recipeService.searchRecipeContainingName(name);
        if(recipeFound == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe not found.");
        } else {
            return recipeFound;
        }
    }

    @GetMapping(value = "/api/recipe/search", params = "category")
    public List<Recipe> searchRecipeByCategory(@RequestParam String category){
        List<Recipe> recipeFound = recipeService.searchRecipeByCategory(category);
        if(recipeFound == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recipe not found.");
        } else {
            return recipeFound;
        }
    }
}
