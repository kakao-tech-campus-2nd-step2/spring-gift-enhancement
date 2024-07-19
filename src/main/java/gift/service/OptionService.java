package gift.service;


import gift.entity.Option;
import gift.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> findAll() {
        return optionRepository.findAll();
    }

    public Option findById(Long id) {

    }

    public void save(Option option) {

    }

}
