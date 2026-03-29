package mediflow.service.notification.Config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "mediflow.service.notification.Repository",
        transactionManagerRef = "appTransactionManager",
        entityManagerFactoryRef = "appEntityManager"
)

public class AppDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource appDataSource() {
        DataSource ds = dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();

        System.out.println("=== APP DATASOURCE URL: " +
                dataSourceProperties().getUrl());
        System.out.println("=== APP DATASOURCE USERNAME: " +
                dataSourceProperties().getUsername());

        return ds;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean appEntityManager(
            @Qualifier("appDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");

        return builder
                .dataSource(dataSource)
                .packages("mediflow.service.notification.Domain.Entity")
                .persistenceUnit("app")
                .properties(props)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager appTransactionManager(
            @Qualifier("appEntityManager") EntityManagerFactory emf
    ){
        return new JpaTransactionManager(emf);
    }
}
