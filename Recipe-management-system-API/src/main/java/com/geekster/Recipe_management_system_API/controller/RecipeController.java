package com.geekster.Recipe_management_system_API.controller;

import com.geekster.Recipe_management_system_API.model.Recipe;
import com.geekster.Recipe_management_system_API.service.AuthenticationService;
import com.geekster.Recipe_management_system_API.service.RecipeService;
import com.geekster.Recipe_management_system_API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping
    public String addRecipe(@Valid @RequestBody Recipe recipe, @Valid @RequestParam String email, @RequestParam String token) {
        if(authenticationService.authenticate(email, token)) {
            return recipeService.createRecipe(recipe,email);
        }else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("/all_recipes")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/recipe_by-name")
    public List<Recipe> getRecipesByName(@RequestParam String recipeName) {
        return recipeService.getRecipesByName(recipeName);
    }

    @PutMapping("update/recipe")
    public Object updateRecipeByName(@Valid @RequestParam String email, @RequestParam String token, @RequestBody Recipe recipe) {
        if(authenticationService.authenticate(email, token)) {
            return recipeService.updateRecipe(recipe,email);
        }else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("delete/recipe")
    public Object deleteRecipeByName(@Valid @RequestParam String email, @RequestParam String token, @RequestParam String recipeName) {
        if (authenticationService.authenticate(email,token)) {
            boolean deletionStatus = recipeService.deleteRecipeByName(recipeName,email);
            if (deletionStatus) {
                return "Recipe deleted successfully!!!";
            } else {
                return "Recipe not found or doesn't belong to the user!!!";
            }
        } else {
            return "Not an Authenticated user activity!!!";
        }
    }

}
