package gift.service;

import gift.model.Option;
import gift.repository.OptionReposityory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OptionService {
    private final OptionReposityory optionReposityory;

    public OptionService(OptionReposityory optionReposityory) {
        this.optionReposityory = optionReposityory;
    }

    public void deleteOption(Long id, String optionName){
        Option deleteOption = optionReposityory.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 옵션이 없습니다."));
        Option newOption = deleteOption.delete(optionName);
        optionReposityory.save(newOption);
    }

    public void addOption(Long id, String optionName){
        Option addOption = optionReposityory.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 옵션이 없습니다."));
        Option newOption = addOption.add(optionName);
        optionReposityory.save(newOption);
    }

    public void updateOption(Long id, String oldName, String newName){
        Option updateOption = optionReposityory.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 옵션이 없습니다."));
        Option newOption = updateOption.update(oldName, newName);
        optionReposityory.save(newOption);
    }
}
