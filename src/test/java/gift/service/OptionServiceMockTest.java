package gift.service;

import gift.repository.product.OptionRepository;
import gift.service.product.OptionService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OptionServiceMockTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;


}
