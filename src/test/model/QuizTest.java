package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    Quiz quiz;
    Question question1;
    Question question2;

    @BeforeEach
    public void runBefore() {
        quiz = new Quiz("Quiz A");
        question1 = new Question("What is 1+1?", "1", "2", "3", "4", "2");
        question2 = new Question("What is 1+1?", "1", "2", "3", "4", "2");
    }

    @Test
    public void testQuizConstructor() {
        assertEquals("Quiz A", quiz.getQuizName());
        assertTrue(quiz.getQuestions().isEmpty());
    }

    @Test
    public void testAddQuestion() {
        quiz.addQuestion(question1);
        assertEquals(question1, quiz.getQuestions().get(0));
        assertEquals(1, quiz.getQuestions().size());
        assertTrue(quiz.getQuestions().contains(question1));

        quiz.addQuestion(question1);
        assertEquals(question1, quiz.getQuestions().get(0));
        assertEquals(1, quiz.getQuestions().size());
        assertTrue(quiz.getQuestions().contains(question1));

        quiz.addQuestion(question2);
        assertEquals(question2, quiz.getQuestions().get(1));
        assertEquals(2, quiz.getQuestions().size());
        assertTrue(quiz.getQuestions().contains(question2));
    }

    @Test
    public void testRemoveQuestion() {
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.removeQuestion(question1);

        assertEquals(1, quiz.getQuestions().size());
        assertFalse(quiz.getQuestions().contains(question1));
        assertEquals(question2, quiz.getQuestions().get(0));
    }

    @Test
    public void testToString() {
        assertEquals("Quiz A", quiz.toString());
    }
}
