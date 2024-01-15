package persistence;

import model.Question;
import model.Quiz;
import model.QuizLibrary;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends persistence.JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            QuizLibrary quizLibrary = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyQuizLibrary() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyQuizLibrary.json");
        try {
            QuizLibrary quizLibrary = reader.read();
            assertEquals(0, quizLibrary.getQuizzes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderYesQuizNoQuestionQuizLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderYesQuizNoQuestionQuizLibrary.json");
        try {
            QuizLibrary quizLibrary = reader.read();
            ArrayList<Quiz> quizzes = quizLibrary.getQuizzes();
            assertEquals(2, quizzes.size());
            checkQuiz("QuizA", quizzes.get(0));
            checkQuiz("QuizB", quizzes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderYesQuizYesQuestionQuizLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderYesQuizYesQuestionQuizLibrary.json");

        try {
            QuizLibrary quizLibrary = reader.read();
            assertEquals("Claire's quiz library", quizLibrary.getName());
            ArrayList<Quiz> quizzes = quizLibrary.getQuizzes();
            assertEquals(2, quizzes.size());
            checkQuiz("QuizA", quizzes.get(0));
            checkQuiz("QuizB", quizzes.get(1));

            Quiz quizA = quizzes.get(0);

            ArrayList<Question> questions1 = quizA.getQuestions();
            assertEquals(2, questions1.size());

            Question question1 = questions1.get(0);
            Question question2 = questions1.get(1);
            assertEquals("What is 1+1?", question1.getText());
            assertEquals("1", question1.getOptionA());
            assertEquals("2", question1.getOptionB());
            assertEquals("3", question1.getOptionC());
            assertEquals("4", question1.getOptionD());
            assertEquals("B", question1.getAnswer());
            assertEquals("What is 1+2?", question2.getText());
            assertEquals("2", question2.getOptionA());
            assertEquals("3", question2.getOptionB());
            assertEquals("4", question2.getOptionC());
            assertEquals("5", question2.getOptionD());
            assertEquals("B", question2.getAnswer());

            ArrayList<Question> questions2 = quizzes.get(1).getQuestions();
            Question question3 = questions2.get(0);
            assertEquals(1, questions2.size());

            assertEquals("What is 2+2?", question3.getText());
            assertEquals("2", question3.getOptionA());
            assertEquals("3", question3.getOptionB());
            assertEquals("4", question3.getOptionC());
            assertEquals("5", question3.getOptionD());
            assertEquals("C", question3.getAnswer());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}