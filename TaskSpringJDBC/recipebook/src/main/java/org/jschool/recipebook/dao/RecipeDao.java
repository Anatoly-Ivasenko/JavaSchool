package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.Product;
import org.jschool.recipebook.model.Recipe;

import java.util.List;

public interface RecipeDao {

    void createRecipe(Recipe recipe);

    Recipe findRecipeByTitle(String title);

    List<Recipe> findRecipesByProduct(Product product);

    List<Recipe> getAllRecipes();
}
