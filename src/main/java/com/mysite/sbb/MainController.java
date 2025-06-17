package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신 것을 환영합니다."; // This will resolve to src/main/resources/templates/index.html
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello"; // This will resolve to src/main/resources/templates/hello.html
    }
}
