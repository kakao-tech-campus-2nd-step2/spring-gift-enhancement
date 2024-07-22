package gift.option.controller;

import gift.global.annotation.ProductInfo;
import gift.global.dto.ProductInfoDto;
import gift.option.dto.OptionRequestDto;
import gift.option.service.OptionService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @Parameter(name = "product_id", required = true)
    public void createOption(@RequestBody OptionRequestDto optionRequestDto, @ProductInfo
        ProductInfoDto productInfoDto) {
        optionService.insertOption(optionRequestDto, productInfoDto);
    }
}
