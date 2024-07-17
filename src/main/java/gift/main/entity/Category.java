package gift.main.entity;

import gift.main.dto.CategoryRequest;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private int uniNumber;

    @Column(nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,  cascade = CascadeType.DETACH)
    private List<Product> Products;
    //카테고리내에 상품이 존재하는 경우 -> 삭제 불가능

    public Category() {
    }

    public Category(CategoryRequest categoryRequest) {
        this.name = categoryRequest.name();
        this.uniNumber = categoryRequest.uniNumber();
    }


    public void updateCategory(CategoryRequest categoryRequest) {
        this.name = categoryRequest.name();
        this.uniNumber = categoryRequest.uniNumber();
    }



    public long getId() {
        return id;
    }

    public int getUniNumber() {
        return uniNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", uniNumber=" + uniNumber +
                ", name='" + name + '\'' +
                ", Products=" + Products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
