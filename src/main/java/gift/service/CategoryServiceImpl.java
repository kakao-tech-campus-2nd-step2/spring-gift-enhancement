package gift.service;

import gift.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<CategoryDTO> categories = Arrays.asList(
        new CategoryDTO(1, "교환권", "#6c95d1", "https://png.pngtree.com/png-vector/20230330/ourmid/pngtree-red-ticket-icon-vector-illustration-png-image_6674739.png", ""),
        new CategoryDTO(2, "상품권", "#6c95d1", "https://imagescdn.gettyimagesbank.com/500/201809/a11186062.jpg", ""),
        new CategoryDTO(3, "뷰티", "#6c95d1", "https://img.imbc.com/adams/Corner/20175/131391670413239055.jpg", ""),
        new CategoryDTO(4, "패션", "#6c95d1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKm65ftNFrjmtS-ahj9sPyZ7W_ONShws9GfQ&s", ""),
        new CategoryDTO(5, "식품", "#6c95d1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1Xh0h-1FVvS5d0Wxvj38Hu7kBs4KSpUyyPw&s", ""),
        new CategoryDTO(6, "리빙/도서", "#6c95d1", "https://lh3.googleusercontent.com/proxy/xsIQupZPagSILU71LwcOmBv4BvStIWOnZuS1-vy_wsZqunSeuXtfJEDzd8Tpshyr1IbCoSRiw-3-BweYOThwoU-z2Eud1o9Fevnx46M9SIM5keznCps5uZOTOmO6agFuqhVA89QifFtJUyd30ybyWFYkqKo5VDC-", ""),
        new CategoryDTO(7, "레저/스포츠", "#6c95d1", "https://aiartshop.com/cdn/shop/files/a-cat-is-surfing-on-big-wave-ai-painting-316.webp?v=1711235460", ""),
        new CategoryDTO(8, "아티스트/캐릭터", "#6c95d1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7CwAhw_DpRjfwSpfzUFKdP-G80zGatPK7Jg&s", ""),
        new CategoryDTO(9, "유아동/반려", "#6c95d1", "https://img1.newsis.com/2023/07/12/NISI20230712_0001313626_web.jpg", ""),
        new CategoryDTO(10, "디지털/가전", "#6c95d1", "https://img.animalplanet.co.kr/news/2020/12/18/700/m264759705v49k3nxgn9.jpg", ""),
        new CategoryDTO(11, "카카오프렌즈", "#6c95d1", "https://blog.kakaocdn.net/dn/cadWYP/btrhVsFqMqq/Q3HWGGl726G9Fs0vN93Dj0/img.jpg", ""),
        new CategoryDTO(12, "트렌드 선물", "#6c95d1", "https://cdn.hankyung.com/photo/202405/01.36897265.1.jpg   ", ""),
        new CategoryDTO(13, "백화점", "#6c95d1", "https://i.namu.wiki/i/uRAh-N8MXDIeQzBevIneGDBTIpZ4JLuqY2POyA-GmqdHcNUr_qjTmhwKcint-NK04dq2d03viq8CRT5cXtkKGA.webp", "")
    );

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categories;
    }

    @Override
    public CategoryDTO getCategoryById(int id) {
        return categories.stream()
            .filter(category -> category.getId() == id)
            .findFirst()
            .orElse(null);
    }
}

