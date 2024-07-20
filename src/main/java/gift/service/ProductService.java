package gift.service;

import gift.DTO.ProductDTO;
import gift.aspect.CheckProductExists;
import gift.entity.ProductEntity;
import gift.mapper.ProductMapper;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    ProductRepository productRepository;

    ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

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
    @CheckProductExists
    @Transactional(readOnly = true)
    public ProductDTO getProduct(Long id) {
        var productEntity = productRepository.findById(id).get();
        return productMapper.toProductDTO(productEntity);
    }

    @CheckProductExists
    @Transactional(readOnly = true)
    public ProductEntity getProductEntity(Long id) {
        return productRepository.findById(id).get();
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
    @CheckProductExists
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * 주어진 상품을 갱신함
     *
     * @param productDTO 갱신할 상품 객체
     */
    @CheckProductExists
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
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
     * 사용자 ID를 통해 사용자의 상품 목록을 가져옵니다.
     *
     * @param pageable 페이지 정보
     * @return ProductDTO 목록
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(Pageable pageable) {
        var productEntities = productRepository.findAll(pageable).toList();
        return productEntities.stream().map(productMapper::toProductDTO).toList();
    }
}
