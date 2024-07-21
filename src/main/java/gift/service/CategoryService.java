package gift.service;

import gift.dto.category.CategoryDTO;
import gift.entity.Category;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public void save(Category category) {
        categoryRepository.findByName(category.getName()).ifPresent(c -> { throw new BadRequestException("이미 존재하는 카테고리"); });
        categoryRepository.save(category);
    }

    @Transactional
    public void update(CategoryDTO categoryDTO) {
        Category category =categoryRepository.findById(categoryDTO.id()).orElseThrow(()->new NotFoundException("존재하지 않는 카테고리입니다."));
        categoryRepository.findByName(categoryDTO.name()).ifPresent(c -> { throw new BadRequestException("이미 존재하는 카테고리"); });
        category.updateCategoryName(categoryDTO.name());
    }

    public void delete(int categoryId) {
        Category category =categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException("존재하지 않는 카테고리입니다."));
        if(!category.getProducts().isEmpty())
            throw new BadRequestException("해당 카테고리에 물품이 존재합니다.");
        categoryRepository.delete(category);
    }

    public Page<CategoryDTO> getCategory(Pageable pageable) {
        return categoryRepository.findAllCategory(pageable);
    }
}
