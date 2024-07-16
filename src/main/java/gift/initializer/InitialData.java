package gift.initializer;

import gift.entity.Category;
import gift.entity.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialData implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public InitialData(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        initialCategory();
        initialProduct();
    }

    private void initialCategory() {
        categoryRepository.save(new Category("교환권", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("상품권", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("뷰티", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("패션", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("리빙/도서", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("레저/스포츠", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("아티스트/캐릭터", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("유아동/반려", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("디지털/가전", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("카카오프렌즈", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("트랜드 선물", "color", "imageUrl", "description"));
        categoryRepository.save(new Category("백화점", "color", "imageUrl", "description"));
    }

    private void initialProduct() {
        productRepository.save(new Product("Ice Americano", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg", categoryRepository.getReferenceById(1L)));
        productRepository.save(new Product("Latte", 5500, "https://cdn.pixabay.com/photo/2023/07/08/13/17/coffee-8114518_1280.png", categoryRepository.getReferenceById(1L)));
        productRepository.save(new Product("Sandwich", 7700, "https://cdn.pixabay.com/photo/2023/08/12/02/58/sandwich-8184642_1280.png", categoryRepository.getReferenceById(1L)));
        productRepository.save(new Product("Cupcake", 10000, "https://cdn.pixabay.com/photo/2023/05/31/14/41/ai-generated-8031574_1280.png", categoryRepository.getReferenceById(1L)));
        productRepository.save(new Product("Chair", 150000, "https://i5.walmartimages.com/seo/Ktaxon-Modern-Single-Sofa-Chair-Club-Chairs-with-Side-Bags-Fabric-Arm-Chair-with-Wood-Legs-for-Living-Room-Bed-Room-Navy-Blue_088241ad-b15b-459c-9555-ef39fb140029.ac38734266e6fcb1f82674b61a537aca.jpeg", categoryRepository.getReferenceById(5L)));
        productRepository.save(new Product("Ryan Figure", 10000, "https://img.danawa.com/prod_img/500000/156/603/img/7603156_1.jpg?_v=20200720155431", categoryRepository.getReferenceById(10L)));
    }
}
