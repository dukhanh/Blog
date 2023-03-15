package com.example.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T>{
    private List<T> content;
    private int page;
    private int size;
    private long totoElements;
    private long totalPages;
    private boolean isFirst;
    private boolean isLast;

}
