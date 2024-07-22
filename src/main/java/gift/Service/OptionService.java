package gift.Service;

import gift.Entity.Option;
import gift.Mapper.Mapper;
import gift.Model.OptionDto;
import gift.Model.OrderRequestDto;
import gift.Repository.OptionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {

    private final OptionJpaRepository optionJpaRepository;
    private final Mapper mapper;

    @Autowired
    public OptionService(OptionJpaRepository optionJpaRepository, Mapper mapper) {
        this.optionJpaRepository = optionJpaRepository;
        this.mapper = mapper;
    }

    public List<OptionDto> getAllOptionsByProductId(long productId) {
        List<Option> options = optionJpaRepository.findAllByProductId(productId);
        return options.stream()
                .map(mapper::optionToDto)
                .collect(Collectors.toList());
    }

    public OptionDto getOptionById(long optionId) {
        Option option = optionJpaRepository.findById(optionId).orElseThrow();
        return mapper.optionToDto(option);
    }

    public void addOption(OptionDto optionDto) {
        Option option = mapper.optionDtoToEntity(optionDto);
        optionJpaRepository.save(option);
    }

    public void updateOption(OptionDto optionDto) {
        Option option = mapper.optionDtoToEntity(optionDto);
        optionJpaRepository.save(option);
    }

    public void deleteOption(long id) {
        optionJpaRepository.deleteById(id);
    }

    public void subtractOption(OrderRequestDto orderRequestDto) {
        if(orderRequestDto.getQuantity() <= 0) return; // 주문 수량이 0 이하인 경우 return
        Option option = optionJpaRepository.findById(orderRequestDto.getOptionId()).orElseThrow(); // 기존 Option 찾기
        option.setQuantity(option.getQuantity() - orderRequestDto.getQuantity()); //기존 수량에서 주문 수량을 빼준다.
        optionJpaRepository.save(option); // 뺀 이후 update
    }
}
