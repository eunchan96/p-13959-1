package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class QuestionRepositoryTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	@DisplayName("findAll")
	void t1() {
		List<Question> questions = questionRepository.findAll();
		assertThat(questions.size()).isEqualTo(2);

		Question question = questions.get(0);
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("findById")
	void t2() {
		Question question = questionRepository.findById(1).get();
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("findBySubject")
	void t3() {
		Question question = questionRepository.findBySubject("sbb가 무엇인가요?").get();
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t4() {
		Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.").get();
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("findBySubjectLike")
	void t5() {
		List<Question> questions = questionRepository.findBySubjectLike("sbb%");

		Question question = questions.get(0);
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("수정")
	void t6() {
		Question question = questionRepository.findById(1).get();
		question.setSubject("수정된 제목");
		questionRepository.save(question);

		Question foundedQuestion = questionRepository.findBySubject("수정된 제목").get();
		assertThat(foundedQuestion).isNotNull();
	}

	@Test
	@DisplayName("삭제")
	void t7() {
		assertThat(questionRepository.count()).isEqualTo(2);

		Question question = questionRepository.findById(1).get();
		questionRepository.delete(question);

		assertThat(questionRepository.count()).isEqualTo(1);
	}

	@Test
	@DisplayName("답변 저장")
	void t8() {
		Question question = questionRepository.findById(2).get();

		Answer answer = new Answer();
		answer.setContent("네, 자동으로 생성됩니다.");
		answer.setQuestion(question);
		answerRepository.save(answer);

		assertThat(question.getAnswers().size()).isEqualTo(1);
	}

	@Test
	@DisplayName("답변 저장 ver.2") // OneToMany 사용
	void t9() {
		Question question = questionRepository.findById(2).get();

		int beforeCount = question.getAnswers().size();

		Answer newAnswer = question.addAnswer("네, 자동으로 생성됩니다.");

		int afterCount = question.getAnswers().size();

		assertThat(newAnswer.getId()).isEqualTo(0); // INSERT가 트랜잭션 이후에 발생하므로 ID는 0으로 시작
		assertThat(afterCount).isEqualTo(beforeCount + 1);
	}

	@Test
	@DisplayName("답변 조회")
	void t10() {
		Answer answer = answerRepository.findById(1).get();
		assertThat(answer.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("답변 조회 ver.2") // Question에서 답변 조회
	void t11() {
		Question question = questionRepository.findById(2).get();
		List<Answer> answers = question.getAnswers();
		assertThat(answers.size()).isEqualTo(1);

		assertThat(answers.get(0).getId()).isEqualTo(1);
	}
}
