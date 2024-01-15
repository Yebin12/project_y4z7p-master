package ui;

import model.Question;
import model.Quiz;
import model.QuizLibrary;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// Quiz Tool for make quiz option
public class MakeQuizTool extends QuizTool implements ListSelectionListener {
    public MakeQuizTool(QuizLibrary quizLibrary) throws FileNotFoundException {
        super(quizLibrary);
    }

    // MODIFIES: this
    // EFFECTS: print out list of quiz name on left panel and question of the selected quiz on right panel
    public void printQuiz() {
        initializeJFrame(splitPane);

        ArrayList<Quiz> quizzes = quizLibrary.getQuizzes();

        for (Quiz q : quizzes) {
            if (!quizModel.contains(q)) {
                quizModel.addElement(q);
            }
        }
        quizJList.getSelectionModel().addListSelectionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: display questions of selected quiz on a right side of a split panel
    @Override
    public void valueChanged(ListSelectionEvent e) {
        for (Question question : previous) {
            if (questionModel.contains(question)) {
                questionModel.removeElement(question);
            }
        }

        quiz = quizJList.getSelectedValue();

        ArrayList<Question> questions = quiz.getQuestions();
        previous = questions;

        questionJList.setModel(questionModel);

        for (Question question : questions) {
            if (!questionModel.contains(question)) {
                questionModel.addElement(question);
            }
        }
    }
}
