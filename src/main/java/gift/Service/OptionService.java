package gift.Service;

import gift.ConverterToDto;
import gift.DTO.Option;
import gift.DTO.OptionDto;
import gift.Repository.OptionRepository;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

  private final OptionRepository optionRepository;

  public OptionService(OptionRepository optionRepository) {
    this.optionRepository = optionRepository;
  }

  public OptionDto addOption(OptionDto optionDto) {
    Option option = new Option(optionDto.getName(), optionDto.getQuantity());
    Option addedOption = optionRepository.save(option);

    return ConverterToDto.convertToOptionDto(addedOption);
  }

  public List<OptionDto> getAllOptions() {
    List<Option> options = optionRepository.findAll();
    List<OptionDto> optionDtos = options.stream().map(ConverterToDto::convertToOptionDto).toList();

    return optionDtos;
  }

  public OptionDto getOptionById(Long id) {
    Option option = optionRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 데이터가 없습니다", 1));

    return ConverterToDto.convertToOptionDto(option);
  }

  public OptionDto deleteOption(Long id) {
    Option option = optionRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 데이터가 없습니다", 1));
    optionRepository.deleteById(id);

    return ConverterToDto.convertToOptionDto(option);
  }

  public OptionDto updateOption(Long id, OptionDto updateOptionDto) {
    Option existingOption = optionRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 데이터가 없습니다", 1));
    Option updateOption = new Option(id, updateOptionDto.getName(), updateOptionDto.getQuantity());
    Option updatedOption = optionRepository.save(updateOption);

    return ConverterToDto.convertToOptionDto(updatedOption);
  }
}
