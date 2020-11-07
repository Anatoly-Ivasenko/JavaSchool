package org.jschool.recipebook.config;

import org.jschool.recipebook.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.DataSource;

@Configuration
@Import(DataConfiguration.class)
public class JdbcTemplateConfig {


}
