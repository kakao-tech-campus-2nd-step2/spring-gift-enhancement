package gift.converter;

import gift.domain.CategoryName;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryNameConverter implements AttributeConverter<CategoryName, String> {

    @Override
    public String convertToDatabaseColumn(CategoryName attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public CategoryName convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return CategoryName.valueOf(dbData);
    }
}
