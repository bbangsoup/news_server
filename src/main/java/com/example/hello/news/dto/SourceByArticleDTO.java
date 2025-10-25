package com.example.hello.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SourceByArticleDTO {
    private String sourceName;
    private String url;
    private Long count; // 소스별 기사 개수 getCount()
}
