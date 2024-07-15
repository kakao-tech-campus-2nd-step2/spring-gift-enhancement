package gift.service;

import gift.dto.category.ShowCategoryDTO;
import gift.entity.Category;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        Category category =categoryOptional.get();
        if(!category.getProducts().isEmpty())
            throw new BadRequestException("해당 카테고리에 물품이 존재합니다.");
        categoryRepository.delete(category);
    }

    public Page<ShowCategoryDTO> getCategory(Pageable pageable) {
        return categoryRepository.findAllCategory(pageable);
    }
}
