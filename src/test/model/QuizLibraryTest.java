package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizLibraryTest {
    QuizLibrary quizLibrary;
    Quiz quizA;
    Quiz quizB;
    Question question1;
    Question question2;
    Question question3;

    @BeforeEach
    public void runBefore() {
        quizLibrary = new QuizLibrary("Claire's quiz library");
        quizA = new Quiz("Quiz A");
        quizB = new Quiz("Quiz B");
        question1 = new Question("What is 1+1?", "1", "2", "3", "4", "2");
        question2 = new Question("What is 3*3?", "1", "9", "3", "4", "2");
        question3 = new Question("What is 4-1?", "1", "9", "3", "4", "2");
        quizA.addQuestion(question1);
        quizB.addQuestion(question2);
        quizB.addQuestion(question3);
    }

    @Test
    public void quizLibraryTest() {
        assertEquals("Claire's quiz library", quizLibrary.getName());
        assertTrue(quizLibrary.getQuizzes().isEmpty());
    }

    @Test
    public void addQuizTest() {
        quizLibrary.addQuiz(quizA);
        assertEquals(quizA, quizLibrary.getQuizzes().get(0));
        assertEquals(1, quizLibrary.getQuizzes().size());

        quizLibrary.addQuiz(quizB);
        assertEquals(quizA, quizLibrary.getQuizzes().get(0));
        assertEquals(quizB, quizLibrary.getQuizzes().get(1));
        assertEquals(2, quizLibrary.getQuizzes().size());
    }
}
