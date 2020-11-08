package org.jschool.recipebook.dao;

import org.jschool.recipebook.config.DataConfiguration;
import org.jschool.recipebook.model.Product;
import org.jschool.recipebook.model.Recipe;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class RecipeDaoImplTest {

    private final
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(DataConfiguration.class);
    public DataSource dataSource = context.getBean(DataSource.class);
    public RecipeDao recipeDao = context.getBean(RecipeDao.class);
    public ProductDao productDao = context.getBean(ProductDao.class);
    public JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

    @Before
    public void setUp() throws Exception {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/createTables.sql"));
    }

    @After
    public void tearDown() throws Exception {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/dropTables.sql"));
    }

    @Test
    public void testCreateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Борщ");
        recipe.setDescription("Варить борщ из капусты");
        Map<Product,Double> ingredients = new HashMap<>();
        ingredients.put(productDao.findProductByName("Капуста"), 0.5);
        ingredients.put(productDao.findProductByName("Картошка"), 0.7);
        ingredients.put(productDao.findProductByName("Морковь"), 0.4);
        recipe.setIngredients(ingredients);
        recipeDao.createRecipe(recipe);
        System.out.println("Создан: " + recipe);
        List<Recipe> recipesFromDB = jdbcTemplate.query("select * from RECIPE where title='Борщ'", (resultSet, i) -> {
            Recipe newRecipe = new Recipe();
            newRecipe.setId(resultSet.getInt(1));
            newRecipe.setTitle(resultSet.getString(2));
            newRecipe.setDescription(resultSet.getString(3));
            return newRecipe;
        });
        Assert.assertEquals(recipesFromDB.size(),1);
        Recipe recipeFromDB = recipesFromDB.get(0);
        List<Object[]> listIngredientsFromDB = jdbcTemplate.query("select PRODUCT_ID, VALUE from INGREDIENT where RECIPE_ID='1'",
                (resultSet, i) -> {
                    Object[] ingredient = new Object[2];
                    ingredient[0] = resultSet.getInt(1);
                    ingredient[1] = resultSet.getDouble(2);
                    return ingredient;
                });
        Map<Product, Double> ingredientsFromDB = new HashMap<>();
        listIngredientsFromDB.forEach(ingr -> ingredientsFromDB.put(productDao.getProductById((int) ingr[0]),(double) ingr[1]));
        Assert.assertEquals(recipesFromDB.size(),1);
        recipeFromDB.setIngredients(ingredientsFromDB);
        System.out.println("Вычитан из БД: " + recipeFromDB);
        Assert.assertEquals(recipeFromDB, recipe);
    }
}