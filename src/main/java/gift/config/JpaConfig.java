package gift.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "gift.product.repository")
@EntityScan(basePackages = "gift.product.model")
@EnableTransactionManagement
public class JpaConfig {
}