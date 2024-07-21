//package gift.category.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "category")
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String color;
//
//    @Column(name = "img_url")
//    private String imgUrl;
//
//    public Category(String name, String color, String imgUrl) {
//        this.name = name;
//        this.color = color;
//        this.imgUrl = imgUrl;
//    }
//
//    protected Category() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getImgUrl() {
//        return imgUrl;
//    }
//}