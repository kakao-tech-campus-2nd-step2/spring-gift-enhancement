package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.OptionRequest;
import gift.dto.request.ProductRequest;
import gift.dto.request.SubtractOptionRequest;
import gift.dto.request.UpdateProductRequest;
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
import static gift.exception.ErrorCode.DUPLICATE_OPTION_NAME_ERROR;

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

    public String addProduct(ProductRequest requestProduct, OptionRequest requestOption) {
        Category category = categoryRepository.findByName(requestProduct.getCategory()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Option option = optionRepository.save(new Option(requestOption));
        productRepository.save(new Product(requestProduct, category, option));
        return ADD_SUCCESS_MSG;
    }

    public String updateProduct(Long productId, UpdateProductRequest productRequest) {
        Product existingProduct = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Category category = categoryRepository.findByName(productRequest.getCategory()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        productRepository.save(new Product(existingProduct.getId(), productRequest, category));
        return UPDATE_SUCCESS_MSG;
    }

    public String deleteProduct(Long productId) {
        productRepository.delete(productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND)));
        return DELETE_SUCCESS_MSG;
    }

    public List<Option> getOptions(Long productId) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        return optionRepository.findAllByProduct(product);
    }

    public String addOption(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Option option = new Option(optionRequest, product);
        List<Option> options = optionRepository.findAllByProduct(product);
        isNewOptionName(options, option);
        optionRepository.save(option);
        return ADD_OPTION_SUCCESS_MSG;
    }

    public void subtractOptionQuantity(Long productId, SubtractOptionRequest subtractOptionRequest) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Option option = optionRepository.findAllByProductAndName(product, subtractOptionRequest.optionName()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        option.subtract(subtractOptionRequest.amount());
    }

    private void isNewOptionName(List<Option> options, Option requestOption){
        for (Option option : options) {
            if (option.getName().equals(requestOption.getName())) {
                throw new CustomException(DUPLICATE_OPTION_NAME_ERROR);
            }
        }
    }
}
