package gift.Service;

import gift.DTO.CategoryDTO;
import gift.DTO.ProductDTO;
import gift.Entity.CategoryEntity;
import gift.Entity.ProductEntity;
import gift.Repository.CategoryRepository;
import gift.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = convertToEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
        return convertToDTO(categoryEntity);
    }

    public CategoryDTO findById(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다"));
        return convertToDTO(category);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity existingCategory = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다"));
        existingCategory.setName(categoryDTO.getName());
        // 카테고리는 이름만 바뀐다고 가정.
        categoryRepository.save(existingCategory);
        return convertToDTO(existingCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO(categoryEntity.getId(), categoryEntity.getName());
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setParentId(categoryEntity.getParent() != null ? categoryEntity.getParent().getId() : null);

        List<CategoryDTO> children = categoryEntity.getChildren().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        categoryDTO.setChildren(children);

        List<ProductDTO> products = categoryEntity.getProducts().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
        categoryDTO.setProducts(products);

        return categoryDTO;
    }

    private CategoryEntity convertToEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setName(categoryDTO.getName());

        return categoryEntity;
    }

    private ProductDTO convertToProductDTO(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());

        return productDTO;
    }
}
