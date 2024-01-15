package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    Question question;

    @BeforeEach
    public void runBefore(){
        question = new Question("What is 1+1?", "1", "2", "3", "4", "2");
    }

    @Test
    public void testQuestionConstructor(){
        assertEquals("What is 1+1?", question.getText());
        assertEquals("1", question.getOptionA());
        assertEquals("2", question.getOptionB());
        assertEquals("3", question.getOptionC());
        assertEquals("4", question.getOptionD());
        assertEquals("2", question.getAnswer());
    }

    @Test
    public void testToString() {
        assertEquals("<html><p align=left>Question: What is 1+1?<br>OptionA) " +
                "1<br>OptionB) 2<br>OptionC) 3<br>OptionD) 4</p></html>", question.toString());
    }
}
