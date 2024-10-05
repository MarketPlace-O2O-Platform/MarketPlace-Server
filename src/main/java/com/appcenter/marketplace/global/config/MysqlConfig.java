package com.appcenter.marketplace.global.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@Transactional 사용가능
@EnableTransactionManagement
//JPA 리포지토리 활성 범위
@EnableJpaRepositories(basePackages = {"com.appcenter.marketplace.domain"})
public class MysqlConfig{

    @Bean(name = "mysqlDataSource")
    // 기본 데이터베이스 설정
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    // DataSource를 통해 직접 jdbc 드라이버로 DB연결을 할 필요가 없고 관리해준다.
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("mysqlDataSource") DataSource dataSource) {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder.dataSource(dataSource).packages("com.appcenter.marketplace.domain").persistenceUnit("primary").properties(properties).build();
    }

    @Primary
    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
