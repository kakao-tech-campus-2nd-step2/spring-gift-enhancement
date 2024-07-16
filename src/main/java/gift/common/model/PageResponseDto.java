package gift.common.model;

import java.util.List;
import org.springframework.data.domain.Pageable;

public class PageResponseDto<T> {

    private List<T> response;
    private Integer pageNumber;
    private Integer pageSize;

    public PageResponseDto(List<T> response, Integer pageNumber, Integer pageSize) {
        this.response = response;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public List<T> getResponse() {
        return response;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public static <T> PageResponseDto<T> of(List<T> response, Pageable pageable) {
        return new PageResponseDto<>(response, pageable.getPageNumber(), pageable.getPageSize());
    }
}
