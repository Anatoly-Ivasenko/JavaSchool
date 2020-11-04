package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class RecipeDaoImpl implements RecipeDao{

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private LobHandler lobHandler;
    private final SimpleJdbcInsert insertRecipe;


    @Autowired
    public RecipeDaoImpl(DataSource dataSource, LobHandler lobHandler, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.lobHandler = lobHandler;
        this.insertRecipe = new SimpleJdbcInsert(dataSource)
                .withTableName("recipes")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void createRecipe(Recipe recipe) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(recipe);
        Number result = insertRecipe.execute(params);
    }

    @Override
    public Recipe findRecipeByTitle(String title) {
        return null;
    }

    @Override
    public List<Recipe> findRecipesByProduct(String productName) {
        return null;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return null;
    }
}
