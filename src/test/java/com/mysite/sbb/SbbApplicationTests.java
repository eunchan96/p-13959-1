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
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("findAll")
	void t1() {
		List<Question> all = questionRepository.findAll();
		assertThat(all.size()).isEqualTo(2);

		Question q = all.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("findById")
	void t2() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
		}
	}

	@Test
	@DisplayName("findBySubject")
	void t3() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertThat(q.getId()).isEqualTo(1);

		// findBySubjectAndContent
		q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertThat(q.getId()).isEqualTo(1);

		// findBySubjectLike
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		q = qList.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}
}
