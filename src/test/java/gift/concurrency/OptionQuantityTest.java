package gift.concurrency;

import gift.entity.Option;
import gift.exception.InsufficientOptionQuantityException;
import gift.repository.OptionRepository;
import gift.service.OptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("옵션 수량 감소 동시성 테스트")
public class OptionQuantityTest {

    @InjectMocks
    private OptionService optionService;
    @Mock
    private OptionRepository optionRepository;

    @Test
    @DisplayName("100개 스레드가 비동기식으로 차감 시도")
    void whenSubtractTwoThread0() {
        //Given
        Option targetOption = new Option("option", 1000);
        when(optionRepository.findByIdWithPessimisticWriteLock(any())).thenReturn(Optional.of(targetOption));

        int subtractQuantity = 10;
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        //When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() ->
                    optionService.subtractOptionQuantity(any(), subtractQuantity)
            );
        }

        executorService.shutdown();

        //Then
        assertEquals(0, targetOption.getQuantity());
    }

    @Test
    @DisplayName("100개 스레드가 비동기식으로 차감 시도 - 0개가 되면 예외처리 하는가")
    void whenSubtractTwoThread1() {
        //Given
        Option targetOption = new Option("option", 1000);
        when(optionRepository.findByIdWithPessimisticWriteLock(any())).thenReturn(Optional.of(targetOption));

        int subtractQuantity = 100;
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Future<?>> futures = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();

        //When
        for (int i = 0; i < threadCount; i++) {
            futures.add(executorService.submit(() -> {
                try {
                    optionService.subtractOptionQuantity(any(), subtractQuantity);
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }));
        }

        executorService.shutdown();

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }

        //Then
        assertThat(targetOption.getQuantity()).isEqualTo(0);
        assertThat(exceptions).hasSize(90)
                .first()
                .isInstanceOf(InsufficientOptionQuantityException.class);
    }
}
