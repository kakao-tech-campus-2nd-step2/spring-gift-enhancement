package gift.config;

import gift.domain.Category;
import gift.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
        return args -> {
            categoryRepository.save(new Category.Builder().name("교환권")
                    .color("#FF5733")
                    .imageUrl("https://example.com/images/exchange_voucher.jpg")
                    .description("다양한 상품으로 교환 가능한 교환권")
                    .build());

            categoryRepository.save(new Category.Builder().name("상품권")
                    .color("#33FF57")
                    .imageUrl("https://example.com/images/gift_card.jpg")
                    .description("쇼핑에 사용할 수 있는 상품권")
                    .build());

            categoryRepository.save(new Category.Builder().name("뷰티")
                    .color("#FF33A1")
                    .imageUrl("https://example.com/images/beauty.jpg")
                    .description("화장품 및 뷰티 제품")
                    .build());

            categoryRepository.save(new Category.Builder().name("패션")
                    .color("#3357FF")
                    .imageUrl("https://example.com/images/fashion.jpg")
                    .description("최신 유행 패션 아이템")
                    .build());

            categoryRepository.save(new Category.Builder().name("식품")
                    .color("#FF8C33")
                    .imageUrl("https://example.com/images/food.jpg")
                    .description("맛있는 음식과 식품")
                    .build());

            categoryRepository.save(new Category.Builder().name("리빙/도서")
                    .color("#33D1FF")
                    .imageUrl("https://example.com/images/living_books.jpg")
                    .description("생활용품과 도서")
                    .build());

            categoryRepository.save(new Category.Builder().name("레저/스포츠")
                    .color("#FF3333")
                    .imageUrl("https://example.com/images/leisure_sports.jpg")
                    .description("레저 및 스포츠 용품")
                    .build());

            categoryRepository.save(new Category.Builder().name("아티스트/캐릭터")
                    .color("#33FFDA")
                    .imageUrl("https://example.com/images/artist_character.jpg")
                    .description("아티스트 및 캐릭터 상품")
                    .build());

            categoryRepository.save(new Category.Builder().name("유아동/반려")
                    .color("#FFC733")
                    .imageUrl("https://example.com/images/kids_pets.jpg")
                    .description("유아 및 반려동물 용품")
                    .build());

            categoryRepository.save(new Category.Builder().name("디지털/가전")
                    .color("#6A33FF")
                    .imageUrl("https://example.com/images/digital_electronics.jpg")
                    .description("디지털 및 가전 제품")
                    .build());

            categoryRepository.save(new Category.Builder().name("카카오프렌즈")
                    .color("#FF9533")
                    .imageUrl("https://example.com/images/kakao_friends.jpg")
                    .description("카카오프렌즈 관련 상품")
                    .build());

            categoryRepository.save(new Category.Builder().name("트렌드 선물")
                    .color("#33FF6A")
                    .imageUrl("https://example.com/images/trend_gift.jpg")
                    .description("최신 트렌드 선물")
                    .build());

            categoryRepository.save(new Category.Builder().name("백화점")
                    .color("#3336FF")
                    .imageUrl("https://example.com/images/department_store.jpg")
                    .description("백화점에서 구매 가능한 상품")
                    .build());
        };

    }
}
