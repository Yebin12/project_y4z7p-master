package ui;

import model.Question;
import model.Quiz;
import model.QuizLibrary;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

// represents a GUI of quiz system
public class QuizTool {
    protected QuizLibrary quizLibrary;
    protected Quiz quiz;
    protected JFrame frame = new JFrame();
    protected JLabel labelImage;
    protected ImageIcon image;
    protected JSplitPane splitPane = new JSplitPane();
    protected JList<Quiz> quizJList = new JList<>();
    protected JList<Question> questionJList = new JList<>();
    protected DefaultListModel<Quiz> quizModel = new DefaultListModel<>();
    protected DefaultListModel<Question> questionModel = new DefaultListModel<>();
    protected String input;
    protected JScrollPane questionScrollPane = new JScrollPane(questionJList);
    protected JScrollPane quizScrollPane = new JScrollPane(quizJList);
    protected ArrayList<Question> previous = new ArrayList<>();
    protected JProgressBar progressBar = new JProgressBar(0, 100);

    // EFFECTS: constructs a constructor and sets up a split panel
    public QuizTool(QuizLibrary quizLibrary) {
        this.quizLibrary = quizLibrary;
        quizJList.setModel(quizModel);
        splitPane.setLeftComponent(quizScrollPane);
        splitPane.setRightComponent(questionScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: sets this quiz library to the given quiz library
    public void setQuizLibrary(QuizLibrary quizLibrary) {
        this.quizLibrary = quizLibrary;
    }

    // MODIFIES: this
    // EFFECTS: sets out a JFrame with a given component
    public void initializeJFrame(Component c) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(c);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: display an input dialog and get user input
    public int displayGetNewIntegerInput(String dialog) {
        input = JOptionPane.showInputDialog(null, dialog);
        int choice = parseInt(input);
        return choice;
    }

    // MODIFIES: this
    // EFFECTS: display an input dialog and get user input
    public String displayGetNewStringInput(String dialog) {
        input = JOptionPane.showInputDialog(null, dialog);
        return input;
    }

    // MODIFIES: this
    // EFFECTS: display a message
    public void displayDialog(String dialog) {
        JOptionPane.showMessageDialog(null, dialog);
    }

    // MODIFIES: this
    // EFFECTS: display a confirmation dialog and get user input
    public int yesOrNo(String dialog) {
        int i = JOptionPane.showConfirmDialog(null, dialog);
        return i;
    }

    // MODIFIES: this
    // EFFECTS: display an image icon
    public void displayImage() {
        image = new ImageIcon(getClass().getResource("smiley.png"));
        labelImage = new JLabel(image);
        initializeJFrame(labelImage);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: displays a progressbar
    public void displayProgressBar() {
        initializeJFrame(progressBar);
        int x = 0;
        while (x < 100) {
            progressBar.setValue(x);
            x++;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x == 100) {
                frame.setVisible(false);
                frame.remove(progressBar);
                displayDialog("We are all set.");
            }
        }
    }
}
