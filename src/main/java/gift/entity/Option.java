package gift.entity;

import gift.dto.OptionResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "options")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "옵션 이름은 필수로 입력해야 합니다.")
    @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력 가능합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_ ]*$", message = "허용되지 않는 특수 문자가 들어가 있습니다.")
	private String name;
	
	@Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
	@Max(value = 99999999, message = "옵션 수량은 1억 개 미만이어야 합니다.")
	@Column(nullable = false)
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public Option(String name, int quantity, Product product) {
		this.name = name;
		this.quantity = quantity;
		this.product = product;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public OptionResponse toDto() {
		return new OptionResponse(id, name, quantity);
	}
}
