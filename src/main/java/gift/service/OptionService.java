package gift.service;

import gift.dto.option.OptionQuantityDTO;
import gift.dto.option.SaveOptionDTO;
import gift.dto.option.UpdateOptionDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OptionRepository optionRepository;

    @Transactional
    public void refill(OptionQuantityDTO optionQuantityDTO) {
        Option option = optionRepository.findById(optionQuantityDTO.id()).orElseThrow(()->new NotFoundException("해당 옵션이 없음"));
        option.addQuantity(optionQuantityDTO.quantity());
    }

    @Transactional
    public void order(OptionQuantityDTO optionQuantityDTO) {
        Option option = optionRepository.findById(optionQuantityDTO.id()).orElseThrow(()->new NotFoundException("해당 옵션이 없음"));
        if(option.getQuantity() < optionQuantityDTO.quantity())
            throw new BadRequestException("재고보다 많은 물건 주문 불가능");
        option.subQuantity(optionQuantityDTO.quantity());
    }

    public void add(SaveOptionDTO saveOptionDTO) {
        Product product = productRepository.findById(saveOptionDTO.product_id()).orElseThrow(()->new NotFoundException("해당 물품이 없음"));
        optionRepository.findByOption(saveOptionDTO.option()).ifPresent(c -> { throw new BadRequestException("이미 존재하는 옵션"); });
        Option option = new Option(product, saveOptionDTO.option());
        optionRepository.save(option);
    }

    public void delete(int id) {
        Option option = optionRepository.findById(id).orElseThrow(()->new NotFoundException("해당 옵션이 없음"));
        Product product = option.getProduct();
        product.deleteOption(option);
        optionRepository.deleteById(id);
    }

    @Transactional
    public void update(UpdateOptionDTO updateOptionDTO) {
        Option option = optionRepository.findById(updateOptionDTO.id()).orElseThrow(()->new NotFoundException("해당 옵션이 없음"));
        optionRepository.findByOption(updateOptionDTO.option()).ifPresent(c -> { throw new BadRequestException("이미 존재하는 옵션"); });
        option.changeOption(updateOptionDTO.option());
    }

}
