package gift.Service;

import gift.Entity.Option;
import gift.Mapper.Mapper;
import gift.Model.OptionDto;
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
        List<Option> options = optionJpaRepository.findAllById(productId);
        return options.stream()
                .map(mapper::optionToDto)
                .collect(Collectors.toList());
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

    public void subtractOption(OptionDto optionDto) {
        Option option = optionJpaRepository.findById(optionDto.getId()).orElseThrow(); // 기존 Option 찾기
        option.setQuantity(option.getQuantity() - optionDto.getQuantity()); //기존 수량에서 주문 수량을 빼준다.
        optionJpaRepository.save(option); // 뺀 이후 update
    }
}
