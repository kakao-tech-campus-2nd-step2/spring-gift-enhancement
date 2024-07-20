package gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gift.model.category.Category;
import gift.model.gift.Gift;
import gift.model.option.Option;
import gift.repository.gift.GiftRepository;
import gift.repository.option.OptionRepository;
import gift.service.option.OptionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

@SpringBootTest
public class OptionServiceConcurrencyTest {

    @Autowired
    private OptionService optionService;

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private OptionRepository optionRepository;

    private Gift gift;
    private Option option;

    @BeforeEach
    void setUp() {
        Category category = new Category(30L, "testCategory", "testCategory", "testCategory", "testCategory");
        Gift gift = new Gift("Test Gift", 1000, "test.jpg", category);
        Option option = new Option("Test Option", 10);
        gift.addOption(option);

        giftRepository.save(gift);
        optionRepository.save(option);

        this.gift = gift;
        this.option = option;
    }

    @Test
    @DisplayName("낙관적 락을 이용한 옵션 수량차감 동시성 테스트")
    void testSubtractOptionToGiftWithOptimisticLock() throws InterruptedException {
        int initialQuantity = option.getQuantity();
        int subtractQuantity = 1;
        int threadCount = 10;

        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                while (true) {
                    try {
                        optionService.subtractOptionToGift(gift.getId(), option.getId(), subtractQuantity);
                        latch.countDown();
                        break;
                    } catch (OptimisticLockingFailureException e) {
                        System.out.println("낙관적 락 발생");
                    } catch (Exception e) {
                        latch.countDown();
                        break;
                    }
                }
            });
        }

        latch.await();

        Option updatedOption = optionRepository.findById(option.getId()).orElseThrow();
        System.out.println("처음 수량 : " + initialQuantity);
        System.out.println("최종 수량 : " + updatedOption.getQuantity());

        assertEquals(initialQuantity - (threadCount * subtractQuantity), updatedOption.getQuantity());
    }
}