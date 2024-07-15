package gift.service;

import gift.entity.Category;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public void save(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent())
            throw new BadRequestException("이미 존재하는 카테고리입니다.");
        categoryRepository.save(category);
    }

    public void update(Category category) {
        if(categoryRepository.findById(category.getId()).isEmpty())
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        categoryRepository.updateCategoryName(category.getId(), category.getName());
    }

    public void delete(int categoryId) {
        if(categoryRepository.findById(categoryId).isEmpty())
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        categoryRepository.deleteById(categoryId);
    }

    public Page<Category> getCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
