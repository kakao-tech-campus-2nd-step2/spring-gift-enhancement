package gift.doamin.category.dto;

import gift.doamin.category.entity.Category;

public class CategoryParam {

    private Long id;

    private String name;

    public CategoryParam(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
