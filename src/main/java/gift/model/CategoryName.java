package gift.model;

import java.util.List;

public class CategoryName {
    private final String name;

    static List<String> categoryNames = List.of(new String[]{
            "교환권", "상품권", "뷰티", "패션", "식품", "리빙/도서", "레저/스포츠",
            "아티스트/캐릭터", "유아동/반려", "디지털/가전", "카카오프렌즈", "트렌드 선물", "백화점"
    });

    public CategoryName(String name){
        this.name = name;

        if(!categoryNames.contains(name)){
            throw new IllegalArgumentException("잘못된 카테고리 입니다.");
        }
    }

    public static List<String> getCategoryList() {
        return categoryNames;
    }

    public String getName(){
        return name;
    }
}
