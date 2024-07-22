package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.AddOptionRequest;
import gift.dto.request.AddProductRequest;
import gift.dto.request.SubtractOptionRequest;
import gift.dto.request.UpdateProductRequest;
import gift.dto.response.MessageResponse;
import gift.exception.CustomException;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.constant.Message.*;
import static gift.exception.ErrorCode.DATA_NOT_FOUND;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public MessageResponse addProduct(AddProductRequest request) {
        Category category = categoryRepository.findByName(request.category()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Option option = new Option(request);
        productRepository.save(new Product(request, category, option));
        return new MessageResponse(ADD_SUCCESS_MSG);
    }

    public MessageResponse updateProduct(Long productId, UpdateProductRequest productRequest) {
        Product existingProduct = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Category category = categoryRepository.findByName(productRequest.category()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        productRepository.save(new Product(existingProduct.getId(), productRequest, category));
        return new MessageResponse(UPDATE_SUCCESS_MSG);
    }

    public MessageResponse deleteProduct(Long productId) {
        productRepository.delete(productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND)));
        return new MessageResponse(DELETE_SUCCESS_MSG);
    }

    public List<Option> getOptions(Long productId) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        return optionRepository.findAllByProduct(product);
    }

    public MessageResponse addOption(Long productId, AddOptionRequest addOptionRequest) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Option option = new Option(addOptionRequest, product);
        optionRepository.save(option);
        return new MessageResponse(ADD_OPTION_SUCCESS_MSG);
    }

    public void subtractOptionQuantity(Long productId, SubtractOptionRequest subtractOptionRequest) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Option option = optionRepository.findAllByProductAndName(product, subtractOptionRequest.optionName()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        option.subtract(subtractOptionRequest.amount());
    }
}
