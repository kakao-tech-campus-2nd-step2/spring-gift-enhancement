package gift.converter;

import gift.dto.CategoryDTO;
import gift.dto.NameDTO;
import gift.dto.ProductDTO;
import gift.model.Category;
import gift.model.Name;
import gift.model.Product;

public class ProductConverter {

    public static ProductDTO convertToDTO(Product product) {
        NameDTO nameDTO = NameConverter.convertToDTO(product.getName());
        return new ProductDTO(product.getId(), nameDTO, product.getPrice(), product.getImageUrl(), product.getCategoryId());
    }

    public static Product convertToEntity(ProductDTO productDTO) {
        Name name = NameConverter.convertToEntity(productDTO.getName());
        return new Product(productDTO.getId(), name, productDTO.getPrice(), productDTO.getImageUrl(), productDTO.getCategoryId());
    }
}