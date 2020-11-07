package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDaoImpl implements RecipeDao{

    private final ProductDao productDao;
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private LobHandler lobHandler;
    private final SimpleJdbcInsert insertRecipe;
    private final SimpleJdbcCall jdbcCall;


    public RecipeDaoImpl(DataSource dataSource, LobHandler lobHandler, JdbcTemplate jdbcTemplate, ProductDao productDao) {
        this.productDao = productDao;
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.lobHandler = lobHandler;
        this.insertRecipe = new SimpleJdbcInsert(dataSource)
                .withTableName("recipe")
                .usingGeneratedKeyColumns("id");
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("getRecipe");
    }

    @Override
    public void createRecipe(Recipe recipe) {
        Number returnId = insertRecipe.executeAndReturnKey(new BeanPropertySqlParameterSource(recipe));
        recipe.setId((int) returnId);
    }

    @Override
    public Recipe findRecipeByTitle(String title) {
        Recipe recipe = new Recipe();
        Map<String, Object> resultCall =
                jdbcCall.execute(new MapSqlParameterSource().addValue("title", title));
        int recipeId = (int) resultCall.get("id");
        recipe.setId(recipeId);
        recipe.setTitle((String) resultCall.get("title"));
        recipe.setDescription((String) resultCall.get("description"));
        recipe.setIngredients(getIngredients(recipeId));
        return recipe;
    }

    @Override
    public List<Recipe> findRecipesByProduct(Product product) {
        return null;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return null;
    }

    private Map<Product, Double> getIngredients(int recipeId) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return namedParameterJdbcTemplate
                .query("select product_id, value from INGREDIENT where recipe_id=:recipeId",
                        new MapSqlParameterSource("recipeId", recipeId), resultSet -> {
                            Map<Product, Double> ingredients = new HashMap<>();
                            while (resultSet.next()){
                                Product product = productDao.getProductById(resultSet.getInt(1));
                                ingredients.put(product, resultSet.getDouble(2));
                            }
                            return ingredients;
                        });
    }
}
