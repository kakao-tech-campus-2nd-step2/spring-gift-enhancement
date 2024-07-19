package gift.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.DuplicateOptionException;
import gift.exception.InvalidProductException;
import gift.exception.InvalidUserException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;

@Service
public class OptionService {

	private final OptionRepository optionRepository;
	private final ProductRepository productRepository;

	public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
		this.optionRepository = optionRepository;
		this.productRepository = productRepository;
	}

	public List<OptionResponse> getOptions(long id) {
		List<Option> options = optionRepository.findByProductId(id);
		return toOptionResponses(options);
	}
	
	public void addOption(long id, OptionRequest request, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		
		Product product = findProductById(id);
		validateDuplicateOption(id, request.getName());
		
		Option option = request.toEntity(product);
		optionRepository.save(option);
	}
	
	private void validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidUserException(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

	private Product findProductById(long id) {
    	return productRepository.findById(id)
    			.orElseThrow(() -> new InvalidProductException("Product not found"));
    }

	private List<OptionResponse> toOptionResponses(List<Option> optinos) {
		List<OptionResponse> OptionResponses = new ArrayList<>();
		for (Option option : optinos) {
			OptionResponse response = option.toDto();
			OptionResponses.add(response);
		}
		return OptionResponses;
	}
	
	private void validateDuplicateOption(long id, String optionName) {
		if (optionRepository.findByProductIdAndName(id, optionName).isPresent()) {
			throw new DuplicateOptionException("Options with the same name cannot appear within a product.");
		}
	}
}
