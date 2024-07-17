package gift.service;

import gift.common.exception.DuplicateDataException;
import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.CreateOptionRequest;
import gift.controller.dto.request.UpdateOptionRequest;
import gift.controller.dto.response.OptionResponse;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getAllOptions(Long id) {
        return optionRepository.findAllByProductIdFetchJoin(id).stream()
                .map(OptionResponse::from)
                .toList();
    }

    @Transactional
    public void save(CreateOptionRequest request) {
        checkProductExist(request.productId());
        Product product = productRepository.getReferenceById(request.productId());
        Option option = new Option(request.name(), request.quantity(), product);
        optionRepository.save(option);
    }

    @Transactional
    public void updateById(UpdateOptionRequest request) {
        checkOptionExist(request.id());
        Option option = optionRepository.getReferenceById(request.id());
        checkDuplicateName(option.getName(), request.name());
        option.updateOption(request.name(), request.quantity());
    }

    @Transactional(readOnly = true)
    public OptionResponse findByIdFetchJoin(Long id) {
        Option option = optionRepository.findByIdFetchJoin(id)
                .orElseThrow(() -> new EntityNotFoundException("Option with id " + id + " not found"));
        return OptionResponse.from(option);
    }

    @Transactional
    public void deleteById(Long id) {
        optionRepository.deleteById(id);
    }

    private void checkProductExist(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
    }

    private void checkOptionExist(Long id) {
        if(!optionRepository.existsById(id)) {
            throw new EntityNotFoundException("Option with id " + id + " not found");
        }
    }

    private void checkDuplicateName(String origin, String name) {
        if (!origin.equals(name) && optionRepository.existsByName(name)) {
            throw new DuplicateDataException("Option with name " + name + " already exists");
        }
    }
}
