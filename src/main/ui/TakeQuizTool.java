package ui;

import model.Question;
import model.Quiz;
import model.QuizLibrary;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// Quiz tool for take quiz class
public class TakeQuizTool extends QuizTool implements ListSelectionListener {
    public TakeQuizTool(QuizLibrary quizLibrary) throws FileNotFoundException {
        super(quizLibrary);
    }

    // MODIFIES: this
    // EFFECTS: display the list of quizzes for user to choose
    public void printQuiz() {
        initializeJFrame(quizScrollPane);

        ArrayList<Quiz> quizzes = quizLibrary.getQuizzes();

        for (Quiz q : quizzes) {
            if (!quizModel.contains(q)) {
                quizModel.addElement(q);
            }
        }
        quizJList.getSelectionModel().addListSelectionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: display questions and input panel for user to type an answer and check answer for each question
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int i = 0;
        quiz = quizJList.getSelectedValue();

        ArrayList<Question> questions = quiz.getQuestions();

        for (Question question : questions) {
            i++;
            String dialog = "Please enter your choice:" + "\n"
                    + "Question" + i + ") " + question.getText() + "\n"
                    + "A) " + question.getOptionA() + "\n"
                    + "B) " + question.getOptionB() + "\n"
                    + "C) " + question.getOptionC() + "\n"
                    + "D) " + question.getOptionD();

            String answer = displayGetNewStringInput(dialog);
            answer = answer.toUpperCase();
            if (answer.equals(question.getAnswer())) {
                displayDialog("Correct!");
            } else {
                displayDialog("Wrong!");
            }
        }
    }
}
