package gift.feat.product.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Long price;
	private String imageUrl;

	@ManyToOne @NotNull
	@JoinColumn(name = "category_id")
	private Category category;

	protected Product() {
	}
	private Product(String name, Long price, String imageUrl, Category category) {
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
		this.category = category;
	}

	public static Product of(String name, Long price, String imageUrl, Category category) {
		return new Product(name, price, imageUrl, category);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Product product))
			return false;
		return Objects.equals(id, product.id) && Objects.equals(name, product.name)
			&& Objects.equals(price, product.price) && Objects.equals(imageUrl, product.imageUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, imageUrl);
	}
}
