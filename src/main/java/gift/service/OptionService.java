package gift.service;

import gift.dto.GetOptionDTO;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionReposityory;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OptionService {
    private final OptionReposityory optionReposityory;
    private final ProductRepository productRepository;

    public OptionService(OptionReposityory optionReposityory, ProductRepository productRepository) {
        this.optionReposityory = optionReposityory;
        this.productRepository = productRepository;
    }

    @Transactional
    public void deleteOption(String option, Long productId){
        List<Option> optionList = optionReposityory.findAllByProduct_Id(productId);
        if(optionList.size() <= 1){
            throw new IllegalArgumentException("옵션은 한개 이상 존재해야 합니다.");
        }
        optionReposityory.deleteByName(option);

        List<Option> NewOptionList = optionReposityory.findAllByProduct_Id(productId);
        updateQuantity(-1, NewOptionList);
    }

    public void addOptions(String options, Long productID){
        List<String> optionList = List.of(options.split(","));
        if(optionList.size() != new HashSet<>(optionList).size()){
            throw new IllegalArgumentException("옵션은 중복될 수 없습니다.");
        }
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));
        for(int i=0; i<optionList.size(); i++){
            Option addOption = new Option(
                    optionList.get(i),
                    optionList.size(),
                    product
            );
            optionReposityory.save(addOption);
        }
    }

    public void addOption(String name, Long productID){
        List<Option> optionList = optionReposityory.findAll();
        List<String> optionNameList = optionList.stream()
                .map(Option::getName)
                .toList();
        if(optionNameList.contains(name)){
            throw new IllegalArgumentException("옵션은 중복될 수 없습니다.");
        }
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));
        Option addOption = new Option(
                name,
                optionList.get(0).getQuantity() + 1,
                product
        );
        optionReposityory.save(addOption);
        List<Option> NewOptionList = optionReposityory.findAllByProduct_Id(productID);
        updateQuantity(1, NewOptionList);
    }

    public void updateOption(String oldName, String newName, long productID){
        Option updateOption = optionReposityory.findByName(oldName)
                .orElseThrow(() -> new NoSuchElementException("해당 옵션이 없습니다."));
        List<Option> optionList = optionReposityory.findAllByProduct_Id(productID);
        List<String> optionNameList = optionList.stream()
                .map(Option::getName)
                .toList();
        if(optionNameList.contains(newName)){
            throw new IllegalArgumentException("옵션은 중복될 수 없습니다.");
        }
        Option newOption = updateOption.update(newName);
        optionReposityory.save(newOption);
    }

    public void removeOption(Long productId, int num){
        List<Option> optionList = optionReposityory.findAllByProduct_Id(productId);
        if(optionList.size() <= 1){
            throw new IllegalArgumentException("옵션은 한개 이상 존재해야 합니다.");
        }
        List<Long> optionIdList = optionList.stream()
                .map(Option::getId)
                .toList();
        for(int i=0; i<num; i++){
            optionReposityory.deleteById(optionIdList.get(i));
        }
        List<Option> NewOptionList = optionReposityory.findAllByProduct_Id(productId);
        updateQuantity(num, NewOptionList);
    }

    public GetOptionDTO getOptions(Long productId){
        List<Option> optionList = optionReposityory.findAllByProduct_Id(productId);
        List<String> optionNameList = optionList.stream()
                .map(Option::getName)
                .toList();
        GetOptionDTO getOptionDTO = new GetOptionDTO(
                productId,
                optionNameList
        );
        return getOptionDTO;
    }

    private void updateQuantity(int num, List<Option> optionList){
        for(int i=0; i<optionList.size(); i++){
            Option newOption = optionList.get(i).quantityUpdate(num);
            optionReposityory.save(newOption);
        }
    }
}
