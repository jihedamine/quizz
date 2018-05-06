package quizz;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionTest {

    @Test
    void testEmptyFile() throws Exception {
        URL url = ClassLoader.getSystemResource("emptyFile.qsn");
        List<Question> questions = QuestionFileParser.getQuestions(Paths.get(url.toURI()));
        assertEquals(0, questions.size());
    }

    @Test
    void testOneInvalidQuestion() throws Exception {
        URL url = ClassLoader.getSystemResource("oneInvalidQuestion.qsn");
        List<Question> questions = QuestionFileParser.getQuestions(Paths.get(url.toURI()));
        assertEquals(0, questions.size());
    }

    @Test
    void testOneValidQuestion() throws Exception {
        URL url = ClassLoader.getSystemResource("oneValidQuestion.qsn");
        List<Question> questions = QuestionFileParser.getQuestions(Paths.get(url.toURI()));
        assertEquals(1, questions.size());
        Question question = questions.get(0);
        assertEquals("Anonymous inner classes always extend directly from the Object class.", question.getQuestion());
        assertEquals(2, question.getWrongAnswers().size());
        assertTrue(question.getWrongAnswers().contains("True"));
        assertTrue(question.getWrongAnswers().contains("Maybe"));
        assertEquals(1, question.getCorrectAnswers().size());
        assertTrue(question.getCorrectAnswers().contains("False"));

        String expectedExplanation = "When you create an anonymous class for an interface, it extends from Object. For example,\n" +
                "\n  *button.addActionListener( new ActionListener() {  \n" +
                "    public void actionPerformed(ActionEvent ae) { } }  \n" +
                "  );*\n\nBut if you make an anonymous class from another class then it extends from that class. For example, consider the following class:\n" +
                "\n*class MyListener implements ActionListener {   \n" +
                "  public void actionPerformed(ActionEvent ae) {\n" +
                "    System.out.println(\\\"MyListener class\\\");\n" +
                "  }\n}\n\n" +
                "button.addActionListener( new MyListener() {\n" +
                "  public void actionPerformed(ActionEvent ae) {\n" +
                "    System.out.println(\\\"Anonymous Listener class\\\");\n" +
                "  }\n});*";
        assertEquals(expectedExplanation, question.getExplanation());
    }

    @Test
    public void testTwoValidQuestions() throws Exception {
        URL url = ClassLoader.getSystemResource("twoValidQuestions.qsn");
        List<Question> questions = QuestionFileParser.getQuestions(Paths.get(url.toURI()));
        assertEquals(2, questions.size());
    }
}
