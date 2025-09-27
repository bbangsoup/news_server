package com.example.hello.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // setter, getter를 만들어 준다.
@NoArgsConstructor  // 매개변수들이 없는 생성자 코드를 생성해준다.
@AllArgsConstructor // 매개변수들이 있는 생성자 코드를 생성해준다.
@Builder // public static builder .... 이런것을 한방에
public class ArticleDTO {
    private SourceDTO source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

}
