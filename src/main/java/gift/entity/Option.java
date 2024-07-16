package gift.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    @Pattern(regexp = "^[a-zA-Z0-9()\\[\\]+\\-&/_]+$", message = "특수기호 안됨")
    @Size(max=50)
    String option;

    int quantity;

    public void setProduct(Product product){
        this.product = product;
    }

    public Option(Product product, String option) {
        this.product = product;
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public Option() {
    }
}