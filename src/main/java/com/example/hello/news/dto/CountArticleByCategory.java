package com.example.hello.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountArticleByCategory {
    private String category;
    private Long count;
}

// com.example.hello.news.dto.CountArticleByCategory
