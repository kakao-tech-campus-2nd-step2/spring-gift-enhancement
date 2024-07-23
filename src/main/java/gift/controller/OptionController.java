package gift.controller;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.service.OptionService;
import gift.vo.Option;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products/")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     *
     * @param id ProductId
     * @return List<Option>
     */
    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponseDto>> getOption(@PathVariable Long id) {
        List<Option> allOptions = optionService.getOptionsPerProduct(id);

        List<OptionResponseDto> allOptionsDtos = allOptions.stream()
                .map(OptionResponseDto::toOptionResponseDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(allOptionsDtos, HttpStatus.OK);
    }

    @PostMapping("/options")
    public ResponseEntity<Void> addOption(@Valid @RequestBody List<OptionRequestDto> optionRequestDtos) {
        optionService.addOption(optionRequestDtos);
        return ResponseEntity.noContent().build();
    }

}
