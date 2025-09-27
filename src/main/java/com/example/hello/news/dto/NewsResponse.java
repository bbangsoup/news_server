package com.example.hello.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private String status;
    private Long totalResults;
    private ArticleDTO[] articles;
}
