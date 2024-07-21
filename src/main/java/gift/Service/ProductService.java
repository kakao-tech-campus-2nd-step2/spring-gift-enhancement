package gift.Service;

import gift.DTO.ProductDTO;
import gift.DTO.WishDTO;
import gift.DTO.CategoryDTO;
import gift.DTO.OptionDTO;
import gift.Entity.ProductEntity;
import gift.Entity.WishEntity;
import gift.Entity.CategoryEntity;
import gift.Entity.OptionEntity;
import gift.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public void deleteProduct(Long id) {
        // ProductEntity에서 WishEntity와의 연관 관계를
        // cascade = CascadeType.ALL로 설정해놓았기 때문에
        // 관련 Wish는 따로 삭제할 필요가 없음.
        productRepository.deleteById(id);
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setImageUrl(productEntity.getImageUrl());
        productDTO.setWishes(convertWishesToDTOs(productEntity.getWishes()));
        productDTO.setCategory(convertToCategoryDTO(productEntity.getCategory()));
        productDTO.setOptions(convertOptionsToDTOs(productEntity.getOptions()));
        return productDTO;
    }

    private List<WishDTO> convertWishesToDTOs(List<WishEntity> wishEntities) {
        return Optional.ofNullable(wishEntities).orElse(List.of())
                .stream()
                .map(wishEntity -> new WishDTO(
                        wishEntity.getId(),
                        wishEntity.getUser().getId(),
                        wishEntity.getProduct().getId(),
                        wishEntity.getProductName()))
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToCategoryDTO(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return new CategoryDTO(
                categoryEntity.getId(),
                categoryEntity.getName());
    }

    private List<OptionDTO> convertOptionsToDTOs(List<OptionEntity> optionEntities) {
        return Optional.ofNullable(optionEntities).orElse(List.of())
                .stream()
                .map(optionEntity -> new OptionDTO(
                        optionEntity.getId(),
                        optionEntity.getName(),
                        optionEntity.getQuantity(),
                        optionEntity.getProduct() != null ? optionEntity.getProduct().getId() : null))
                .collect(Collectors.toList());
    }
}
