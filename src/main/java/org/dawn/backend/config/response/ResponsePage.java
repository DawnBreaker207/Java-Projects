package org.dawn.backend.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResponsePage<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<T> content;

    Pagination pagination;

    public static <T> ResponsePage<T> of(PageResponse<T> page) {
        return new ResponsePage<>(page);
    }

    public static <S, T> ResponsePage<T> of(PageResponse<S> page, Function<S, T> mapper) {
        List<T> content = page
                .getContent()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
        ResponsePage<T> res = new ResponsePage<>();
        res.setContent(content);
        res.setPagination(new Pagination(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
        return res;
    }

    public ResponsePage(PageResponse<T> page) {
        this.content = page.getContent();
        this.pagination = new Pagination(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Pagination {
        Integer pageNumber;
        Integer pageSize;
        Long totalElements;
        Integer totalPages;
    }
}