package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeDaoImpl implements RecipeDao{

    private final ProductDao productDao;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertRecipe;
    private final RowMapper<Recipe> recipeRowMapper;

    public RecipeDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate,
                         NamedParameterJdbcTemplate namedParameterJdbcTemplate, ProductDao productDao) {
        this.productDao = productDao;
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertRecipe = new SimpleJdbcInsert(dataSource)
                .withTableName("recipe")
                .usingGeneratedKeyColumns("id");
        this.recipeRowMapper = (resultSet, i) -> {
            Recipe recipe = new Recipe();
            int recipeId = resultSet.getInt("id");
            recipe.setId(recipeId);
            recipe.setTitle(resultSet.getString("title"));
            recipe.setDescription(resultSet.getString("description"));
            recipe.setIngredients(getIngredients(recipeId));
            return recipe;
        };
    }

    @Override
    public void createRecipe(Recipe recipe) {
        Number returnId = insertRecipe.executeAndReturnKey(new BeanPropertySqlParameterSource(recipe));
        recipe.setId((int) returnId);
        saveIngredients(recipe);
    }

    @Override
    public Recipe findRecipeByTitle(String title) {
        List<Recipe> recipes = namedParameterJdbcTemplate
                .query("select * from RECIPE where title=:title",
                        new MapSqlParameterSource("title", title), recipeRowMapper);
        if (recipes.isEmpty()) {
            System.out.println("No recipe " + title);
        }
        return recipes.get(0);
    }

    public Recipe findRecipeById(int id) {
        List<Recipe> recipes = namedParameterJdbcTemplate
                .query("select * from RECIPE where id=:id",
                        new MapSqlParameterSource("id", id), recipeRowMapper);
        if (recipes.isEmpty()) {
            System.out.println("No recipe with id: " + id);
        }
        return recipes.get(0);
    }

    @Override
    public List<Recipe> findRecipesByProduct(String productName) {
        Product product = productDao.findProductByName(productName);
        int productId = product.getId();
        return namedParameterJdbcTemplate.query("select recipe_id from INGREDIENT where product_id=:productId",
                new MapSqlParameterSource("productId",productId),resultSet -> {
                    List<Recipe> recipeList = new ArrayList<>();
                    while (resultSet.next()) {
                        recipeList.add(findRecipeById(resultSet.getInt(1)));
                    }
                    return recipeList;
                });
    }

    @Override
    public List<Recipe> getAllRecipes() {
        MappingSqlQuery<Recipe> mappingSqlQuery = new MappingSqlQuery<Recipe>(dataSource, "select * from recipe;") {
            @Override
            protected Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
                Recipe recipe = new Recipe();
                int recipeId = resultSet.getInt(1);
                recipe.setId(recipeId);
                recipe.setTitle(resultSet.getString(2));
                recipe.setDescription(resultSet.getString(3));
                recipe.setIngredients(getIngredients(recipeId));
                return recipe;
            }
        };
        return mappingSqlQuery.execute();
    }

    public Map<Product, Double> getIngredients(int recipeId) {
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

    private void saveIngredients(Recipe recipe) {
        Map<Product, Double> ingredients = recipe.getIngredients();
        jdbcTemplate.batchUpdate("insert into INGREDIENT (RECIPE_ID, PRODUCT_ID, VALUE) values (?, ?, ?)",
                ingredients.keySet().stream()
                        .map(product -> new Object[] {recipe.getId(), product.getId(), ingredients.get(product)})
                        .collect(Collectors.toList()));
    }
}
