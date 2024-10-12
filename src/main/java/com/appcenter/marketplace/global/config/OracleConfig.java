package com.appcenter.marketplace.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.appcenter.marketplace.global.oracleRepository"})
public class OracleConfig {

    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "oracle.datasource")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "oracleJdbc")
    // DataSource를 통해 직접 jdbc 드라이버를 사용하진 않지만 jdbcTemplate을 통해 간단하게 db작업을 수행해야 한다.
    public JdbcTemplate jdbcTemplate(@Qualifier("oracleDataSource")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}