package quizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import quizz.repository.QuestionRepository;

@Configuration
@ComponentScan
@EnableJpaRepositories("quizz.repository")
public class Config {

    @Autowired
    QuestionRepository questionRepository;

    @Bean
    public QuestionController questionController() {
        QuestionController questionController = new QuestionController(questionRepository);
        return questionController;
    }
}
