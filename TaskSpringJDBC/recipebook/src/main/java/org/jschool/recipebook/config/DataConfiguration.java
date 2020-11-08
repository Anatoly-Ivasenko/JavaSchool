package org.jschool.recipebook.config;

import org.jschool.recipebook.dao.ProductDao;
import org.jschool.recipebook.dao.ProductDaoImpl;
import org.jschool.recipebook.dao.RecipeDao;
import org.jschool.recipebook.dao.RecipeDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class DataConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataConfiguration.class);
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:~/resources/recipes", "sa", "");
    }

    @Bean JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public ProductDao productDao(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new ProductDaoImpl(dataSource, namedParameterJdbcTemplate);
    }

    @Bean
    public RecipeDao recipeDao(DataSource dataSource, JdbcTemplate jdbcTemplate,
                               NamedParameterJdbcTemplate namedParameterJdbcTemplate, ProductDao productDao) {
        return new RecipeDaoImpl(dataSource, jdbcTemplate, namedParameterJdbcTemplate, productDao);
    }


//    @PostConstruct
//    public void createTables() throws SQLException {
//        ScriptUtils.executeSqlScript(dataSource().getConnection(), new ClassPathResource("/createTables.sql"));
//    }
}
