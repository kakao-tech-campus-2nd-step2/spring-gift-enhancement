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

    @Size(min=0)
    int quantity;

    public Option(Product product, String option) {
        this.product = product;
        this.option = option;
        this.quantity=0;
    }

    public int getId() {
        return this.id;
    }

    public String getOption(){
        return this.option;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public Option() {
    }

    public void addQuantity(int num){
        this.quantity += num;
    }

    public void subQuantity(int num){
        this.quantity -= num;
    }
}