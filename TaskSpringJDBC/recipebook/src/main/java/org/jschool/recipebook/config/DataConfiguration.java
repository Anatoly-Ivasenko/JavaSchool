package org.jschool.recipebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.DataSource;

@Configuration
public class DataConfiguration {

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

}
