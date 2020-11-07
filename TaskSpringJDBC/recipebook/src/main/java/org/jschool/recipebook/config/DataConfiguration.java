package org.jschool.recipebook.config;

import org.jschool.recipebook.dao.ProductDao;
import org.jschool.recipebook.dao.ProductDaoImpl;
import org.jschool.recipebook.dao.RecipeDao;
import org.jschool.recipebook.dao.RecipeDaoImpl;
import org.jschool.recipebook.model.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataConfiguration.class);
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:~/resources/recipes", "sa", "");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LobHandler lobHandler() {
        return new DefaultLobHandler();
    }

    @Bean
    public ProductDao productDao(DataSource dataSource) {
        return new ProductDaoImpl(dataSource);
    }

    @Bean
    public RecipeDao recipeDao(DataSource dataSource, LobHandler lobHandler, JdbcTemplate jdbcTemplate, ProductDao productDao) {
        return new RecipeDaoImpl(dataSource, lobHandler, jdbcTemplate, productDao);
    }




//    @PostConstruct
//    public void createTables() throws SQLException {
//        ScriptUtils.executeSqlScript(dataSource().getConnection(), new ClassPathResource("/createTables.sql"));
//    }
}
