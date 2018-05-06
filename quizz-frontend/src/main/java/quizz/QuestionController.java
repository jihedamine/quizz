package quizz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quizz.repository.QuestionRepository;
import reactor.core.publisher.Flux;

@RestController
public class QuestionController {
    private final QuestionRepository repository;

    public QuestionController(QuestionRepository questionRepository) {
        this.repository = questionRepository;
    }

    @GetMapping("/questions")
    public Flux<Question> getQuestions() {
        return repository.findAll().log();
    }

    @GetMapping(path = "/questions", produces = "application/stream+json")
    public Flux<Question> getQuestionStream() {
        return repository.findQuestionBy().log();
    }

    // WebFlux only
    @PostMapping(path="/questions", consumes = "application/stream+json")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Question> loadQuestions(@RequestBody Flux<Question> questionFlux) {
        return this.repository.insert(questionFlux);
    }


}
