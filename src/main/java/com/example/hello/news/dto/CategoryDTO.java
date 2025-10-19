package com.example.hello.news.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// POJO : Plain Old Java Object

@Getter
@Setter
@NoArgsConstructor
// 실제 화면에 보여줄것이 뭘까? 하면서 제일처음 만들기
public class CategoryDTO {
    private String id;
    private String name;
    private String memo;
}
