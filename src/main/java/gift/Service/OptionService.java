package gift.Service;

import gift.ConverterToDto;
import gift.DTO.Option;
import gift.DTO.OptionDto;
import gift.Repository.OptionRepository;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
  private final OptionRepository optionRepository;

  public OptionService(OptionRepository optionRepository){
    this.optionRepository=optionRepository;
  }

  public OptionDto addOption(OptionDto optionDto) {
    Option option = new Option(optionDto.getName(),optionDto.getQuantity());
    Option addedOption = optionRepository.save(option);

    return ConverterToDto.convertToOptionDto(addedOption);
  }
}
