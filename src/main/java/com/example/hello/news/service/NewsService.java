package com.example.hello.news.service;

import com.example.hello.news.dto.*;
import com.example.hello.news.entity.Article;
import com.example.hello.news.entity.Category;
import com.example.hello.news.entity.Source;
import com.example.hello.news.repository.ArticleRepository;
import com.example.hello.news.repository.CategoryRePository;
import com.example.hello.news.repository.SourceRepository;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {
    // private final DataSourceTransactionManager dataSourceTransactionManager; 얜 뭐였지?
    @Value("${newsapi.source_url}")
    private String sourceURL;

    @Value("${newsapi.apiKey}")
    private String apiKey;

    @Value("${newsapi.article_url}")
    private String articleURL;


    private final CategoryRePository categoryRePository;
    private final SourceRepository sourceRepository;
    private final ArticleRepository articleRepository;

    // @Autowired
    // private CategoryRePository categoryRePository;

    public NewsResponse getGeneral() throws URISyntaxException, IOException, InterruptedException {
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=4066eb57bb084153a41edca64b3c8390";

        // client instance를 생성한다.
        HttpClient client = HttpClient.newBuilder().build();

        // request 인스턴스를 생성한다. (필수: url, method(요청방법))
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        // client에서 request를 보내고 response를 문자열 형태로 받아온다.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String resBody = response.body();

        Gson gson = new Gson();
        NewsResponse newsResponse = gson.fromJson(resBody, NewsResponse.class);
        System.out.println( newsResponse.getStatus() );

        return newsResponse;

    }


    public List<CategoryDTO> getCategories(){
        // categoryRePository.findAll() == select * from category;  ==> fetch
        List<Category> categories = categoryRePository.findAll(); // entity list

        // 비어있는 category dto 리스트 인스턴스를 생성한다.
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(Category category : categories){
            CategoryDTO dto = new CategoryDTO();
            dto.setName(category.getName());
            dto.setMomo(category.getMemo());
            categoryDTOList.add( dto ) ;
        }

        return categoryDTOList;
    }

    public String inputCategory(Category category) {
        if(category != null) {
            try{
                Category saved = categoryRePository.save(category);
                // saved.getName().equals(category.getName());
            } catch(Exception e){
                return String.format("ERROR: %s", e.getMessage());
            }

        return "SUCCESS";

        }
        return "ERROR: 카테고리 정보가 없습니다.";
    }

    @Transactional
    public void inputSources() throws URISyntaxException, IOException, InterruptedException {
        String url = sourceURL + apiKey;
        System.out.println( url );  // source url

        // client instance를 생성한다.
        HttpClient client = HttpClient.newBuilder().build();

        // request 인스턴스를 생성한다. (필수: url, method(요청방법))
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        // client에서 request를 보내고 response를 문자열 형태로 받아온다.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String resBody = response.body();

        Gson gson = new Gson();
        SourceResponse sourceResponse = gson.fromJson(resBody, SourceResponse.class);

        System.out.println( sourceResponse.getStatus() );
        System.out.println( sourceResponse.getSources().length );

        // sourceResponse에 있는 모든 SourceDTO 인스턴스의 데이터를 이용하여
        // Source Entity 인스턴스를 생성하고 데이터베이스에 저장한다.
        // SourceDTO ==> Source
        try {
            for (SourceDTO dto : sourceResponse.getSources()) {
                Source source = new Source();
                source.setSid(dto.getId());
                source.setName(dto.getName());
                source.setDescription(dto.getDescription());
                source.setUrl(dto.getUrl());
                source.setCategory(dto.getCategory());
                source.setLanguage(dto.getLanguage());
                source.setCountry(dto.getCountry());
                sourceRepository.save(source);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<SourceDTO> getSources() {
        // 데이터베이스로부터 Source Entity 리스트를 가져와서
        // 모든 Source Entity 인스턴스를 SourceDTO 인스턴스로 변환하여 반환한다.
        List<Source> sources = sourceRepository.findAll();

        // for(Source source : sources){}

        // stream().foreach( Functional Interface -> 익명 클래스 -> 람다식 )    --> 내부적으로 변환하고 끝냄. 반환하는 값이 없음.
        // stream().map( Functional Interface -> 익명 클래스 -> 람다식 )        --> 변환해서 반환해야 할때.
        
        
        // .map( source -> {Source.toDTO(source)} )

        return sources.stream().map(Source::toDTO).toList();

    }

    @Transactional
    public void inputArticles(String category) throws URISyntaxException, IOException, InterruptedException, RuntimeException{
        String url = String.format("%scategory=%s&%s", articleURL,category,apiKey);
        // --> https://newsapi.org/v2/top-headlines?country=us&=business&apiKey=4066eb57bb084153a41edca64b3c8390
        System.out.println( url );

        // client instance를 생성한다.
        HttpClient client = HttpClient.newBuilder().build();

        // request 인스턴스를 생성한다. (필수: url, method(요청방법))
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        // client에서 request를 보내고 response를 문자열 형태로 받아온다.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String resBody = response.body();

        Gson gson = new Gson();
        NewsResponse newsResponse = gson.fromJson(resBody, NewsResponse.class);
        System.out.println( newsResponse.getStatus() );
        System.out.println( newsResponse.getTotalResults() );
        System.out.println( newsResponse.getArticles()[0].getAuthor() );
        saveArticles(newsResponse, category);
    }

    public void saveArticles(NewsResponse newsResponse,String category){
        try {
            for(ArticleDTO article : newsResponse.getArticles()){

                // 이미 기존에 입력되어 있는 source가 있다면 DB에서 찾아서 인스턴스를 만들고
                // Optional : null 안정성을 보장하는 객체이다
                Optional<Source> srcOpt = sourceRepository.findByName(article.getSource().getName());

                // srcOpt.get()       // <-- 얘가 실제 source 이다. null이 나올 수 있다.

                // 없으면 새로 생성(srcOpt안에 인스턴스의 값이 null임)
                Source src = srcOpt.orElseGet( () -> {
                    Source s1 = new Source();
                    s1.setName(article.getSource().getName());
                    return sourceRepository.save(s1);    // save 하고 그 저장된 인스턴스가 바로 돌아온다
                });

                Optional<Category> catOpt = categoryRePository.findByName(category);
                Category cat = catOpt.orElseGet( () -> {
                    Category c = new Category();
                    c.setName(category);
                    return categoryRePository.save(c);
                });

                Article articel1 = Article.fromDTO( article, src, cat );
                articleRepository.save(articel1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
