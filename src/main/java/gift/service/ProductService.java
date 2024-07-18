package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.product.ModifyProductDTO;
import gift.dto.product.ProductWithOptionDTO;
import gift.dto.product.SaveProductDTO;
import gift.dto.product.ShowProductDTO;

import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.UnAuthException;
import gift.exception.exception.NotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@Service
@Validated
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<ProductWithOptionDTO> getAllProductsWithOption(Pageable pageable) {
        return optionRepository.findAllWithOption(pageable);
    }

    public Page<ShowProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAllProduct(pageable);
    }

    public void saveProduct(SaveProductDTO product) {
        Category category = categoryRepository.findById(product.categoryId()).orElseThrow(()->new NotFoundException("해당 카테고리가 없음"));
        Product saveProduct = new Product(product.name(), product.price(), product.imageUrl(),category);
        List<String> optionList = stream(product.option().split(",")).toList();
        optionList= optionList.stream().distinct().collect(Collectors.toList());

        if(isValidProduct(saveProduct,optionList)){
            saveProduct = productRepository.save(saveProduct);
            category.addProduct(saveProduct);
            addOptionToProduct(optionList,saveProduct);
        }
    }

    private void addOptionToProduct(List<String> optionList, Product product) {
        optionList.stream()
                .map(str -> new Option(product, str))
                .filter(this::isValidOption)
                .forEach(option -> optionRepository.save(option));
    }

    private boolean isValidProduct(@Validated Product product,List<String> optionList){
        if(optionList.isEmpty())
            throw new BadRequestException("하나의 옵션은 필요");
        if(product.getName().contentEquals("카카오"))
            throw new UnAuthException("MD와 상담해주세요.");

        Optional<Product> productOptional = productRepository.findById(product.getId());
        return productOptional.map(value -> value.equals(product)).orElse(true);
    }

    private boolean isValidOption(@Validated Option option){
        if(optionRepository.findById(option.getId()).isPresent())
            throw new BadRequestException("이미 존재하는 옵션입니다.");
        return true;
    }

    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("존재하지 않는 id입니다."));
        product.getCategory().deleteProduct(product);
        optionRepository.deleteAll();
        productRepository.deleteById(id);
    }


    public String getProductByID(int id) {
        Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("해당 물건이 없습니다."));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct="";

        try {
            jsonProduct = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonProduct;
    }

    @Transactional
    public void modifyProduct(ModifyProductDTO modifyProductDTO) {
        Product product = productRepository.findById(modifyProductDTO.id()).orElseThrow(()-> new NotFoundException("물건이 없습니다."));
        product.modifyProduct(modifyProductDTO);
    }

}
