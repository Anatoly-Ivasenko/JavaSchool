package org.jschool.recipebook.dao;

import org.jschool.recipebook.config.DataConfiguration;
import org.jschool.recipebook.model.Recipe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.util.HashMap;


@Component
public class RecipeDaoImplTest {

    private final
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(DataConfiguration.class);
    public DataSource dataSource = context.getBean(DataSource.class);
    public RecipeDao recipeDao = context.getBean(RecipeDao.class);

    @After
    public void tearDown() throws Exception {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/dropTables.sql"));
    }

    @Before
    public void setUp() throws Exception {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/createTables.sql"));
    }

    @Test
    public void testCreateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Борщ");
        recipe.setDescription("Варить борщ из капусты");
        recipe.setIngredients(new HashMap<>());
        recipeDao.createRecipe(recipe);
    }
}