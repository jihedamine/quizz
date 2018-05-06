package quizz.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import quizz.Question;
import reactor.core.publisher.Flux;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, Long> {

    @Tailable
    Flux<Question> findQuestionBy();
}
