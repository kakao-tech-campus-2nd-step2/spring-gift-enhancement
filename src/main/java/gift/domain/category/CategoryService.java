    package gift.domain.category;

    import gift.global.exception.BusinessException;
    import java.util.List;
    import java.util.Optional;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    public class CategoryService {

        private final JpaCategoryRepository categoryRepository;

        public CategoryService(JpaCategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }

        public List<Category> getCategories() {
            List<Category> categories = categoryRepository.findAll();
            return categories;
        }

        public void createCategory(CategoryDTO categoryDTO) {
            if (categoryRepository.existsByName(categoryDTO.getName())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "동일한 이름의 카테고리 존재");
            }

            Category category = new Category(categoryDTO.getName(), categoryDTO.getDescription());
            categoryRepository.save(category);
        }

        public void deleteCategory(Long id) {
            if (categoryRepository.findById(id).isEmpty()) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 카테고리가 존재 X");
            }

            categoryRepository.deleteById(id);
        }

        @Transactional
        public void updateCategory(Long id, CategoryDTO categoryDTO) {
            Optional<Category> findCategory = categoryRepository.findById(id);

            if (findCategory.isEmpty()) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "수정할 카테고리가 존재 X");
            }
            // 이름 중복 검사, 중복되면서 id 가 자신이 아닐 때
            Optional<Category> compareCategory = categoryRepository.findByName(categoryDTO.getName());
            if (compareCategory.isPresent() && compareCategory.get().getId() != id) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 이름의 카테고리 이미 존재");
            }

            findCategory.get().update(categoryDTO.getName(), categoryDTO.getDescription());
        }
    }
