package org.jschool.recipebookonemore.repository;

import org.jschool.recipebookonemore.model.Product;
import org.jschool.recipebookonemore.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    Recipe findByTitle(String title);

    @Query("select r from recipes where key(r.ingredients) = :product")
    List<Recipe> findRecipesByIngredient(@Param("product") Product product);
}
