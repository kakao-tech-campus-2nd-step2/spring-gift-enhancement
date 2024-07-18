package gift.service;

import gift.DTO.Category.CategoryRequest;
import gift.DTO.Category.CategoryResponse;
import gift.domain.Category;
import gift.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    /*
     * 카테고리를 저장하는 로직
     */
    @Transactional
    public void save(CategoryRequest categoryRequest){
        Category category = new Category(
                categoryRequest.getName()
        );

        categoryRepository.save(category);
    }
    /*
     * 카테고리를 전부 불러오는 로직
     */
    public Page<CategoryResponse> findAll(int page, int size){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(CategoryResponse::new);
    }
    /*
     * 카테고리를 갱신하는 로직
     */
    @Transactional
    public void update(Long id, CategoryRequest categoryRequest){
        Category savedCategory = categoryRepository.findById(id).orElseThrow(NullPointerException::new);

        savedCategory.update(categoryRequest.getName());
    }
    /*
     * 카테고리를 삭제하는 로직
     */
    @Transactional
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
