package com.example.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// annotation
//

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "index";
    }

    // GET Method로 이 라우터 경로를 호출함
    @RequestMapping("/signup")
    public String signup(@RequestParam(value = "userid", required = false) String user,
                         @RequestParam(value = "userage", required = false) Long age,
                         Model model){
        System.out.println( user );
        System.out.println( age );

        if (user == null || age == null){
            model.addAttribute("error", "잘 못된 요청 입니다.");
            return "index";
        }

        model.addAttribute("userid", user);
        model.addAttribute("age", age);


        return "index";
    }
    @RequestMapping("/page/{no}")
    public String page(@PathVariable("no")Long pageNo, Model model) {
        System.out.println( pageNo );
        model.addAttribute("page", pageNo);
        return "index";
    }


}
