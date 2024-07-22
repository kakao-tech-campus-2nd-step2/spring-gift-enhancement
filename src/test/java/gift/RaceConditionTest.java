package gift;

import gift.config.RedisConfig;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionTimedOutException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
@Import(RedisConfig.class)
public class RaceConditionTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("동시에 삭제 요청[성공]-Redisson 분산락")
    void subtractRequestAtTheSameTime() throws InterruptedException {
        // given
        int quantity = 100;
        int subtractAmount = 1;
        Category category = categoryRepository.save(new Category("cname", "ccolor", "cImage", ""));
        List<Option> options = List.of(new Option("oName", quantity));
        Product product = productRepository.save(new Product("pName", 0, "purl", category, options));
        Long optionId = product.getOptions().get(0).getId();

        int threadCount = 100; // 스레드 개수
        ExecutorService executorService = Executors.newFixedThreadPool(32); // 스레드 풀 크기
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Product finalProduct = product;
            executorService.submit(() -> {
                try {
                    int q = productService.subtractQuantity(finalProduct.getId(), optionId, subtractAmount);
                } catch (TransactionTimedOutException e) {
                    System.out.println("TimeOut");
                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // when
        product = productRepository.findProductAndOptionByIdFetchJoin(product.getId()).get();
        Option actual = product.findOptionByOptionId(optionId);

        // then
        int expactedQuantity = quantity - subtractAmount * threadCount;
        assertThat(actual.getQuantity()).isEqualTo(expactedQuantity);
    }
}