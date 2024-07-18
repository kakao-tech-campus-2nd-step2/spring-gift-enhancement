package gift.service;

import gift.dto.option.OptionQuantityDTO;
import gift.entity.Option;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OptionRepository optionRepository;

    public void refill(OptionQuantityDTO optionQuantityDTO) {
        Option option = optionRepository.findById(optionQuantityDTO.optionId()).orElseThrow(()->new NotFoundException("해당 옵션이 없음"));
        option.addQuantity(optionQuantityDTO.quantity());
        optionRepository.refill(option.getId(),optionQuantityDTO.quantity());
    }

    public void order(OptionQuantityDTO optionQuantityDTO) {
        Option option = optionRepository.findById(optionQuantityDTO.optionId()).orElseThrow(()->new NotFoundException("해당 옵션이 없음"));
        if(option.getQuantity() < optionQuantityDTO.quantity())
            throw new BadRequestException("재고보다 많은 물건 주문 불가능");
        option.subQuantity(optionQuantityDTO.quantity());
        optionRepository.order(option.getId(),optionQuantityDTO.quantity());
    }
}
