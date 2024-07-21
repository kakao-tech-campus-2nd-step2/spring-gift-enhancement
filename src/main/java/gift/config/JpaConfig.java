package gift.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@EntityScan(basePackages = {
        "gift.category.model",
        "gift.product.model",
        "gift.option.model",
        "gift.wishlist.model",
        "gift.member.model"
})
@EnableJpaRepositories(basePackages = {
        "gift.category.repository",
        "gift.product.repository",
        "gift.option.repository",
        "gift.wishlist.repository",
        "gift.member.repository"
})
@Configuration
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(
                "gift.category.model",
                "gift.product.model",
                "gift.option.model",
                "gift.wishlist.model",
                "gift.member.model"
        );
        em.setJpaVendorAdapter(jpaVendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        em.setJpaProperties(properties);
        return em;
    }
}