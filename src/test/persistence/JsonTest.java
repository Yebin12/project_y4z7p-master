package persistence;

import model.Quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkQuiz(String name, Quiz quiz) {
        assertEquals(name, quiz.getQuizName());
    }
}


