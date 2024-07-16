package gift.common.config;

import gift.entity.Category;
import gift.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {

    @Bean
    public CommandLineRunner loadData(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("교환권", "#6c95d1",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""));
                categoryRepository.save(new Category("상품권", "#6c95d2",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m641/dimm_theme.png", ""));
                categoryRepository.save(new Category("뷰티", "#6c95d3",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m642/dimm_theme.png", ""));
                categoryRepository.save(new Category("패션", "#6c95d4",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m643/dimm_theme.png", ""));
                categoryRepository.save(new Category("식품", "#6c95d5",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m644/dimm_theme.png", ""));
                categoryRepository.save(new Category("리빙/도서", "#6c95d6",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m645/dimm_theme.png", ""));
                categoryRepository.save(new Category("레저/스포츠", "#6c95d7",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m646/dimm_theme.png", ""));
                categoryRepository.save(new Category("아티스트/캐릭터", "#6c95d8",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m647/dimm_theme.png", ""));
                categoryRepository.save(new Category("유아동/반려", "#6c95d9",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m648/dimm_theme.png", ""));
                categoryRepository.save(new Category("디지털/가전", "#6c95da",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m649/dimm_theme.png", ""));
                categoryRepository.save(new Category("카카오프렌즈", "#6c95db",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m650/dimm_theme.png", ""));
                categoryRepository.save(new Category("트렌드 선물", "#6c95dc",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m651/dimm_theme.png", ""));
                categoryRepository.save(new Category("백화점", "#6c95dd",
                    "https://gift-s.kakaocdn.net/dn/gift/images/m652/dimm_theme.png", ""));
            }
        };
    }

}
