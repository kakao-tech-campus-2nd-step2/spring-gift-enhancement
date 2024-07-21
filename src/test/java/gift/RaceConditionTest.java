package gift;

import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionTimedOutException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class RaceConditionTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    @DisplayName("동시에 삭제 요청[성공]-비관적락")
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

                String lockKey = "subtractQuantity:" + finalProduct.getId() + ":" + optionId;
                RLock lock = redissonClient.getLock(lockKey);
                try {
                    // lock 걸기
                    boolean lockAcquired = lock.tryLock(3000, 10000, TimeUnit.MILLISECONDS);
                    if (!lockAcquired) {
                        throw new RuntimeException("Unable to acquire lock for key: " + lockKey);
                    }

                    int q = productService.subtractQuantity(finalProduct.getId(), optionId, subtractAmount);
                    // lock 풀기
                } catch (TransactionTimedOutException e) {
                    System.out.println("TimeOut");
                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock(); // 락 해제
                    }
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