//package gift.option.model;
//
//import gift.product.model.Product;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "product_options", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"product_id","option_name"})
//}) // 상품 id와 옵션 종류(이름)이 유일해야함 -> 옵션 이름 중복 불가능
//public class Option {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    private OptionName name;
//    private OptionQuantity optionQuantity;
//
//    public Option() {}
//
//    public Option(Product product, OptionName name, OptionQuantity optionQuantity) {
//        this.product = product;
//        this.name = name;
//        this.optionQuantity = optionQuantity;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public OptionName getName() {
//        return name;
//    }
//
//    public OptionQuantity getOptionQuantity() {
//        return optionQuantity;
//    }
//}