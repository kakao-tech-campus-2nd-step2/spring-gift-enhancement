package gift.service;

import gift.constants.Messages;
import gift.domain.Category;
import gift.dto.request.CategoryRequest;
import gift.dto.response.CategoryResponse;
import gift.exception.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void save(CategoryRequest categoryDto){
        categoryRepository.save(categoryDto.toEntity());
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id){
        return categoryRepository.findById(id)
                .map(CategoryResponse::from)
                .orElseThrow(()->new CategoryNotFoundException(Messages.NOT_FOUND_CATEGORY));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Transactional
    public void deleteById(Long id){
        categoryRepository.findById(id)
                        .orElseThrow(() -> new CategoryNotFoundException(Messages.NOT_FOUND_CATEGORY));
        categoryRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, CategoryRequest request){
        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(Messages.NOT_FOUND_CATEGORY));

        foundCategory.updateName(request.name());
        foundCategory.setColor(request.color());
        foundCategory.setImageUrl(request.imageUrl());
        foundCategory.setDescription(request.description());

        categoryRepository.save(foundCategory);
    }

}
