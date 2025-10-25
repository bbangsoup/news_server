package com.example.hello.news.repository;

import com.example.hello.news.dto.CountArticleByCategory;
import com.example.hello.news.dto.SourceByArticleDTO;
import com.example.hello.news.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Optional<Article> findByUrl(String url);

    // 카테고리별 기사 개수를 구하는 함수
    @Query("select new com.example.hello.news.dto.CountArticleByCategory(a.category.name, COUNT(a.id)) " +
           "from Article a " +
           "group by a.category.name " +
           "order by COUNT(a.id) desc")
    List<CountArticleByCategory> countArticleByCategory();

    // 소스별 기사 수
    @Query( "select new com.example.hello.news.dto.SourceByArticleDTO(a.source.name, a.source.url, COUNT(a.id)) " +
            "from Article a " +
            "group by a.source.name, a.source.url " +
            "order by count(a.id) desc")
    List<SourceByArticleDTO> countArticleBySource(Pageable pageable);
}
