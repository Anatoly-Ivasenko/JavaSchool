package org.jschool.recipebookonemore.service;

import org.jschool.recipebookonemore.model.Product;
import org.jschool.recipebookonemore.model.Recipe;

import java.util.List;

public interface RecipeBookService {

    List<Product> findAllProducts();

    List<Recipe> findAllRecipes();

    Recipe saveRecipe(Recipe recipe);

    Product saveProduct(Product product);

    Product findProductByName(String name);

    Product findProductById(int id);

    Recipe findRecipeByTitle(String title);

    List<Recipe> findRecipesByProductName(String productName);
}
