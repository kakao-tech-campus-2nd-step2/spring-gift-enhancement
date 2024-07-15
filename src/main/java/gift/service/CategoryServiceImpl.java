package gift.service;

import gift.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<CategoryDTO> getAllCategories() {
        return Arrays.asList(
            new CategoryDTO(1, "교환권", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(2, "상품권", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(3, "뷰티", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(4, "패션", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(5, "식품", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(6, "리빙/도서", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(7, "레저/스포츠", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(8, "아티스트/캐릭터", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(9, "유아동/반려", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(10, "디지털/가전", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(11, "카카오프렌즈", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(12, "트렌드 선물", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", ""),
            new CategoryDTO(13, "백화점", "#6c95d1", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "")
        );
    }
}
