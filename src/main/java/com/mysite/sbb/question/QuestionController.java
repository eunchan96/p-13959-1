package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public String list(Model model) {
        model.addAttribute("name", "paul");
        model.addAttribute("ages", List.of(10, 20, 30, 40, 50));

        return "question_list";
    }
}