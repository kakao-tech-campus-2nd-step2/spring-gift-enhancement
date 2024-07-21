package gift.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import gift.service.product.OptionService;
import gift.service.product.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OptionServiceTest {

    @Autowired
    private OptionService optionService;

    @Autowired
    private ProductService productService;

    @Test
    void 동시에_100의_구매요청() throws InterruptedException {
        // given
        int threadCount = 1000;
        ExecutorService excuterService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        // when

        for (int i = 0; i < threadCount; i++) {
            excuterService.submit(() -> {
                try {
                    optionService.purchaseOption(1L, 1);
                } catch (Exception e) {
                    System.out.println(e.getMessage() + "retry");
                }

            });
        }

        excuterService.shutdown();
        excuterService.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);

        // then
        assertThat(optionService.findOptionById(1L).getQuantity()).isEqualTo(0);

    }


}
