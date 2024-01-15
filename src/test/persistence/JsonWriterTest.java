package persistence;


import model.Question;
import model.Quiz;
import model.QuizLibrary;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            QuizLibrary quizLibrary = new QuizLibrary("Claire's quiz library");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyQuizLibrary() {
        try {
            QuizLibrary quizLibrary = new QuizLibrary("Claire's quiz library");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyQuizLibrary.json");
            writer.open();
            writer.write(quizLibrary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyQuizLibrary.json");
            quizLibrary = reader.read();
            assertEquals("Claire's quiz library", quizLibrary.getName());
            assertEquals(0, quizLibrary.getQuizzes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterYesQuizNoQuestionQuizLibrary() {
        try {
            QuizLibrary quizLibrary = new QuizLibrary("Claire's quiz library");
            quizLibrary.addQuiz(new Quiz("QuizA"));
            quizLibrary.addQuiz(new Quiz("QuizB"));
            JsonWriter writer = new JsonWriter("./data/testWriterYesQuizNoQuestionQuizLibrary.json");
            writer.open();
            writer.write(quizLibrary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterYesQuizNoQuestionQuizLibrary.json");
            quizLibrary = reader.read();
            assertEquals("Claire's quiz library", quizLibrary.getName());
            ArrayList<Quiz> quizzes = quizLibrary.getQuizzes();
            assertEquals(2, quizzes.size());
            checkQuiz("QuizA", quizzes.get(0));
            checkQuiz("QuizB", quizzes.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterYesQuizYesQuestionQuizLibrary() {
        try {
            QuizLibrary quizLibrary = new QuizLibrary("Claire's quiz library");
            Quiz quizA = new Quiz("QuizA");
            Quiz quizB = new Quiz("QuizB");
            Question question1 = new Question("What is 1+1?", "1", "2", "3", "4", "B");
            Question question2 = new Question("What is 1+2?", "2", "3", "4", "5", "B");
            Question question3 = new Question("What is 2+2?", "2", "3", "4", "5", "C");

            quizA.addQuestion(question1);
            quizA.addQuestion(question2);
            quizB.addQuestion(question3);

            quizLibrary.addQuiz(quizA);
            quizLibrary.addQuiz(quizB);

            ArrayList<Question> questionsA = quizA.getQuestions();
            ArrayList<Question> questionsB = quizB.getQuestions();

            JsonWriter writer = new JsonWriter("./data/testWriterYesQuizYesQuestionQuizLibrary.json");
            writer.open();
            writer.write(quizLibrary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterYesQuizYesQuestionQuizLibrary.json");
            quizLibrary = reader.read();
            assertEquals("Claire's quiz library", quizLibrary.getName());
            ArrayList<Quiz> quizzes = quizLibrary.getQuizzes();
            assertEquals(2, quizzes.size());
            checkQuiz("QuizA", quizzes.get(0));
            checkQuiz("QuizB", quizzes.get(1));

            assertEquals(2, questionsA.size());
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

            assertEquals(1, questionsB.size());
            assertEquals("What is 2+2?", question3.getText());
            assertEquals("2", question3.getOptionA());
            assertEquals("3", question3.getOptionB());
            assertEquals("4", question3.getOptionC());
            assertEquals("5", question3.getOptionD());
            assertEquals("C", question3.getAnswer());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}