package gift.concurrencyTest;

import gift.entity.Option;
import gift.repository.OptionRepository;
import gift.service.OptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("옵션 수량 감소 동시성 테스트")
public class OptionQuantityTest {

    @Autowired
    OptionService optionService;
    @Autowired
    OptionRepository optionRepository;

    @Test
    @DisplayName("100개 스레드가 비동기식으로 차감 시도")
    void whenSubtractTwoThread3() throws InterruptedException {
        //Given
        //초기 데이터를 이용. Option의 ID=1L의 수량은 1000개
        int initialQuantity = 1000;
        int subtractQuantity = 10;
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();

        //When
        for (int i = 0; i < threadCount; i++) {
            futures.add(executorService.submit(() -> {
                optionService.subtractOptionQuantity(1L, subtractQuantity);
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        //Then
        Option updatedOption = optionRepository.findById(1L).orElseThrow();
        assertEquals(initialQuantity - (subtractQuantity * threadCount), updatedOption.getQuantity());
    }
}
