package gift.model;

import jakarta.persistence.*;

@Entity
public class Option {
    private static final double MAX_OPTION_NUM = 100000000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "quantity", columnDefinition = "integer not null")
    private long quantity;

    @Column(name = "name", columnDefinition = "varchar(255) not null")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_option_product"), nullable = false)
    private Product product;

    protected Option() {
    }

    public Option(String name, long quantity, Product product){
        isCorrectOptionName(name);
        isCorrectQuantityUpdate(quantity);
        this.product = product;
    }

    public Option(Long id, String name, long quantity, Product product){
        this.id = id;
        isCorrectOptionName(name);
        isCorrectQuantityUpdate(quantity);
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Product getProductID() {
        return product;
    }

    private void isCorrectQuantityUpdate(long quantity){
        if(quantity < 1 || quantity >= MAX_OPTION_NUM){
            throw new IllegalArgumentException("옵션은 1개 이상 1억개 미만이어야 합니다.");

        }
        this.quantity = quantity;
    }

    private void isCorrectOptionName(String name){
        if(name.length()>50){
            throw new IllegalArgumentException("옵션은 최대 50자까지만 입력이 가능합니다.");
        }
        String letters = "()[]+-&/_ ";
        for(int i=0; i<name.length(); i++){
            char one = name.charAt(i);
            if(!Character.isLetterOrDigit(one) && !letters.contains(Character.toString(one))){
                throw new IllegalArgumentException("옵션 내 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");

            }
        }
        this.name = name;
    }

    public Option update(String name){
        isCorrectOptionName(name);
        return this;
    }

    public Option quantityUpdate(int num){
        isCorrectQuantityUpdate(this.quantity + num);
        return this;
    }
}
