package com.example.hello.news.controller;

import com.example.hello.news.dto.NewsResponse;
import com.example.hello.news.service.NewsService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequiredArgsConstructor
public class NewsController {

    // @Autowired  // 자동 주입
    private final NewsService newsService;

    @RequestMapping("/news")
    public String newsHome(Model model) {
        try {
            NewsResponse newsResponse = newsService.getGeneral();
            model.addAttribute("news", newsResponse);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // error 처리
            model.addAttribute("error", e.getMessage());
        }
        return "news";
    }
}
