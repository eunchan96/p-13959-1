package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("수정")
	void t6() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			q.setSubject("수정된 제목");
			this.questionRepository.save(q);
		}

		oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertThat(q.getSubject()).isEqualTo("수정된 제목");
		}
	}

	@Test
	@DisplayName("삭제")
	void t7() {
		assertThat(this.questionRepository.count()).isEqualTo(2);
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			this.questionRepository.delete(q);
		}

		assertThat(this.questionRepository.count()).isEqualTo(1);
	}
}
