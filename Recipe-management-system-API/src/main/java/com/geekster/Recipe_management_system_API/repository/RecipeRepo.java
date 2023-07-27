package com.geekster.Recipe_management_system_API.repository;

import com.geekster.Recipe_management_system_API.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe,Long> {
    Optional<Recipe> findRecipeByRecipeId(Long recipeId);

    Optional<Recipe> findByRecipeNameAndOwnerEmail(String recipeName, String email);

    List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);

}
