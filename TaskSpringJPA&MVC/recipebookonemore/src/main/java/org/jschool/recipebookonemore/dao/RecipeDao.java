package org.jschool.recipebookonemore.dao;

import org.jschool.recipebookonemore.model.Recipe;

import java.util.Collection;

public interface RecipeDao {

    void save(Recipe recipe);

    Recipe findByTitle(String title);

    Collection<Recipe> findAll();
}
