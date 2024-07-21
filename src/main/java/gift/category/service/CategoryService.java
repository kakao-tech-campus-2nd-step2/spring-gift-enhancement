//package gift.category.service;
//
//import gift.category.model.Category;
//import gift.category.repository.CategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class CategoryService {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Transactional
//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//}