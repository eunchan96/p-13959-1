package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    @ResponseBody
    public String list() {
        List<Question> questions = questionRepository.findAll();
        String qustionList = questions.stream()
                .map(q -> "%d / %s".formatted(q.getId(), q.getSubject()))
                .collect(Collectors.joining("\n"));

        return """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>질문 목록</title>
                </head>
                <body>
                    <h1>질문 목록</h1>
                    <ul>
                        %s
                    </ul>
                </body>
                </html>
                """.formatted(qustionList);
    }
}