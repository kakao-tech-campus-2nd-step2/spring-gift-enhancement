package gift.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.dto.OptionRequest;
import gift.dto.OptionResponse;
import gift.service.OptionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product/{id}/options")
public class OptionController {
	
	private final OptionService optionService;
	
	public OptionController(OptionService optionService) {
		this.optionService = optionService;
	}
	
	@GetMapping
	public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable("id") long id){
		List<OptionResponse> options = optionService.getOptions(id);
		return ResponseEntity.status(HttpStatus.OK).body(options);
	}
	
	@PostMapping
	public ResponseEntity<Void> addOption(@PathVariable("id") long id,
			@Valid @RequestBody OptionRequest request, BindingResult bindingResult){
		optionService.addOption(id, request, bindingResult);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
