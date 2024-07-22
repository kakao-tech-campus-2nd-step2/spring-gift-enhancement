package gift.service;

import gift.DTO.ProductDTO;
import gift.DTO.ProductOptionDTO;
import gift.entity.ProductEntity;
import gift.exception.ProductNotFoundException;
import gift.mapper.ProductMapper;
import gift.mapper.ProductOptionMapper;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    /**
     * 새 상품을 생성하고 맵에 저장함
     *
     * @param productDTO 저장할 상품 객체
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        var productEntity = productMapper.toProductEntity(productDTO, false);
        productRepository.save(productEntity);
        return productMapper.toProductDTO(productEntity);
    }

    /**
     * 주어진 ID에 해당하는 상품을 반환함
     *
     * @param id 조회할 상품의 ID
     */
    @Transactional(readOnly = true)
    public ProductDTO getProduct(Long id) {
        var productEntity = productRepository.findById(id)
            .orElseThrow(()-> new ProductNotFoundException("상품이 존재하지 않습니다."));
        return productMapper.toProductDTO(productEntity);
    }

    @Transactional(readOnly = true)
    public ProductEntity getProductEntity(Long id) {
        return productRepository.findById(id)
            .orElseThrow(()-> new ProductNotFoundException("상품이 존재하지 않습니다."));
    }

    /**
     * 모든 상품을 반환함
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        var productEntities = productRepository.findAll();
        return productEntities.stream().map(productMapper::toProductDTO).toList();
    }

    /**
     * 주어진 ID에 해당하는 상품을 삭제함
     *
     * @param id 삭제할 상품의 ID
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!isProdutExit(id)) {
            throw new ProductNotFoundException("상품이 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }

    /**
     * 주어진 상품을 갱신함
     *
     * @param productDTO 갱신할 상품 객체
     */

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (!isProdutExit(id)) {
            throw new ProductNotFoundException("상품이 존재하지 않습니다.");
        }
        var productEntity = productMapper.toProductEntity(productDTO);
        productEntity.setId(id);
        productRepository.save(productEntity);
        return productMapper.toProductDTO(productEntity);
    }

    public void updateProductEntity(Long id, ProductEntity productEntity) {
        productEntity.setId(id);
        productRepository.save(productEntity);
    }

    /**
     * 사용자의 상품 목록을 가져옵니다.
     *
     * @param pageable 페이지 정보
     * @return ProductDTO 목록
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(Pageable pageable) {
        var productEntities = productRepository.findAll(pageable).toList();
        return productEntities.stream()
                .map(productMapper::toProductDTO).toList();
    }

    /**
     * 상품 옵션을 반환함
     *
     * @param id 옵션을 조회할 상품의 ID
     */
    @Transactional(readOnly = true)
    public List<ProductOptionDTO> getProductOptions(Long id){
        var productEntity = productOptionRepository.findById(id).get().getProductEntity();
        return productEntity.getProductOptions().stream()
                .map(productOptionMapper::toProductOptionDTO).toList();
    }

    /**
     * 상품 옵션을 추가함
     *
     * @param productOptionDTO 추가할 상품 옵션 객체
     * @param productId        상품 옵션을 추가할 상품의 ID
     */
    @Transactional
    public ProductOptionDTO addProductOption(Long productId, ProductOptionDTO productOptionDTO){
        var productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        if (productEntity.getProductOptions().stream()
            .anyMatch(option -> option.getName().equals(productOptionDTO.name()))) {
            throw new IllegalArgumentException("이미 존재하는 옵션입니다.");
        }

        var productOptionEntity = productOptionMapper.toProductOptionEntity(productOptionDTO, productEntity, false);
        productOptionRepository.save(productOptionEntity);

        return productOptionMapper.toProductOptionDTO(productOptionEntity);
    }

    /**
     * 상품 옵션을 삭제함
     *
     * @param id 삭제할 상품 옵션의 ID
     */
    @Transactional
    public void deleteProductOption(Long id){
        if (!isProdutExit(id)) {
            throw new ProductNotFoundException("옵션이 존재하지 않습니다");
        }
        productOptionRepository.deleteById(id);
    }

    /**
     * 상품 옵션을 갱신함
     *
     * @param id               갱신할 상품 옵션의 ID
     * @param productOptionDTO 갱신할 상품 옵션 객체
     */
    @Transactional
    public void updateProductOption(Long id, ProductOptionDTO productOptionDTO) {
        var productOptionEntity = productOptionRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("옵션이 존재하지 않습니다"));
        productOptionEntity.setName(productOptionDTO.name());
        productOptionEntity.setQuantity(productOptionDTO.quantity());
        productOptionRepository.save(productOptionEntity);
    }

    public boolean isProdutExit(Long id) {
        return productRepository.existsById(id);
    }
}
