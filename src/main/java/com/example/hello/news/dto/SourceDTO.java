package com.example.hello.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data           // setter, getter 만들어줌
@NoArgsConstructor      // 생성자 만들어줌
@AllArgsConstructor     // 필드들을 입력으로 받는 생성자
public class SourceDTO {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
}
