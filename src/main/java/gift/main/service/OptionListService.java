package gift.main.service;

import gift.main.dto.OptionListRequest;
import gift.main.entity.OptionList;
import gift.main.repository.OptionListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionListService {

    @Autowired
    private OptionListRepository optionListRepository;

    @Transactional
    public void addOptionList(Long productId, OptionListRequest optionListRequest) {
        OptionList optionList = new OptionList(productId, optionListRequest.getSize());
        optionListRepository.save(optionList);
    }
}
