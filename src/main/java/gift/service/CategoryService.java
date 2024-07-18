package gift.service;

import gift.dto.CategoryDTO;
import gift.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> readAll();

    void create( CategoryDTO categoryDTO);

    void update(long id,CategoryDTO categoryDTO);

    void delete(long id);
}
