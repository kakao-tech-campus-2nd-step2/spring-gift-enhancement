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

@Entity
@Table(name = "options")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;

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
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public OptionResponse toDto() {
		return new OptionResponse(id, name, quantity);
	}
}
