package com.geekster.Recipe_management_system_API.service;

import com.geekster.Recipe_management_system_API.model.Recipe;
import com.geekster.Recipe_management_system_API.model.User;
import com.geekster.Recipe_management_system_API.repository.RecipeRepo;
import com.geekster.Recipe_management_system_API.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {


    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    UserRepo userRepo;

    public String createRecipe(Recipe recipe, String email) {
        User recipeOwner = userRepo.findFirstByEmail(email);
        if (recipeOwner != null) {
            recipe.setOwner(recipeOwner);
            recipeRepo.save(recipe);
            return "Recipe Posted!!!";
        }else {
            return "User not found";
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    public List<Recipe> getRecipesByName(String recipeName) {
        return recipeRepo.findByRecipeNameContainingIgnoreCase(recipeName);
    }

    public Object updateRecipe(Recipe recipe, String email) {
        User recipeOwner = userRepo.findFirstByEmail(email);
        if(recipeOwner != null) {
            Optional<Recipe> existingRecipe = recipeRepo.findRecipeByRecipeId(recipe.getRecipeId());

            if (existingRecipe.isPresent()) {
                Recipe newRecipe = existingRecipe.get();

                if(newRecipe.getOwner().equals(recipeOwner)) {
                    newRecipe.setRecipeName(recipe.getRecipeName());
                    newRecipe.setIngredients(recipe.getIngredients());
                    newRecipe.setInstructions(recipe.getInstructions());
                    newRecipe.setCookingTimeInMinutes(recipe.getCookingTimeInMinutes());

                    return recipeRepo.save(newRecipe);
                }else {
                    return "Recipe does not belong to the user!!";
                }
            }else {
                return "Recipe not found!!";
            }
        }else {
            return "User not found!!";
        }
    }

    public boolean deleteRecipeByName(String recipeName, String email) {
        Optional<Recipe> optionalRecipe = recipeRepo.findByRecipeNameAndOwnerEmail(recipeName,email);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();

            recipeRepo.delete(recipe);
            return true; // Deletion successful
        }else {
            return false; // Recipe not found or doesn't belong to the user
        }
    }

    public boolean validateRecipe(Recipe recipe) {
        return (recipe!=null && recipeRepo.existsById(recipe.getRecipeId()));
    }
}
