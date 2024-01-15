package ui;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Event;
import model.EventLog;
import model.Quiz;
import model.Question;
import model.QuizLibrary;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

// Quiz making and solving application
public class QuizApp {
    private static final String JSON_STORE = "./data/quizlibrary.json";
    private Scanner input;
    private Quiz quiz;
    private QuizLibrary quizLibrary;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private QuizTool quizTool;
    private MakeQuizTool makeQuizTool;
    private TakeQuizTool takeQuizTool;

    private JFrame frame = new JFrame();
    private JSplitPane split1;
    private JSplitPane split2;
    private JSplitPane split3;
    private JButton b1 = new JButton();
    private JButton b2 = new JButton();
    private JButton b3 = new JButton();
    private JButton b4 = new JButton();
    private JProgressBar progressBar = new JProgressBar();

    // EFFECTS: constructs quizLibrary and runs the quiz application
    public QuizApp() throws FileNotFoundException {
        quizLibrary = new QuizLibrary("Claire's Quiz Library");

        quizTool = new QuizTool(quizLibrary);
        makeQuizTool = new MakeQuizTool(quizLibrary);
        takeQuizTool = new TakeQuizTool(quizLibrary);

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeJComponents();
        runQuiz();
    }

    // MODIFIES: this
    // EFFECTS: initialize JComponents
    public void initializeJComponents() {
        split1 = new JSplitPane();
        split2 = new JSplitPane();
        split3 = new JSplitPane();
        split1.setLeftComponent(split2);
        split1.setRightComponent(split3);
        b1.setText("make a quiz");
        b2.setText("take a quiz");
        b3.setText("view a quiz");
        b4.setText("quit");
        b1.addActionListener(e -> makeQuiz());
        b2.addActionListener(e -> takeQuiz());
        b3.addActionListener(e -> displayQuizLibrary());
        b4.addActionListener(e -> quit());
        split2.setLeftComponent(b1);
        split2.setRightComponent(b2);
        split3.setLeftComponent(b3);
        split3.setRightComponent(b4);
    }

    // MODIFIES: this
    // EFFECTS: ask user to load a quiz AND display main menu
    public void runQuiz() {
        int i = quizTool.yesOrNo("Do you want to load a quiz?");

        if (i == 0) {
            quizLibrary = loadQuizLibrary();
            quizTool.setQuizLibrary(quizLibrary);
            makeQuizTool.setQuizLibrary(quizLibrary);
            takeQuizTool.setQuizLibrary(quizLibrary);
            quizTool.displayProgressBar();
        }
        initializeJFrame(split1);
    }

    // MODIFIES: this
    // EFFECTS: display a smiley image and ends a system
    public void quit() {
        quizTool.displayImage();
        printLog(EventLog.getInstance());
    }

    // MODIFIES: this
    // EFFECTS: make a new quiz object with given quiz name and store the quiz in the quizlibrary
    //          then asks the user to add, remove, or finish making the quiz
    public void makeQuiz() {
        boolean keepGoing = true;
        String name = quizTool.displayGetNewStringInput("Enter the quiz name: ");
        this.quiz = new Quiz(name);

        quizLibrary.addQuiz(quiz);

        while (keepGoing) {
            String dialog = "What do you want to do?" + "\n Add new question? -> Enter 'a': "
                    + "\n Remove a question? -> Enter 'r': " + "\n Finish making a quiz? -> Enter 'f': ";
            String command = quizTool.displayGetNewStringInput(dialog);
            if (command.equals("a")) {
                makeQuestion();
            } else if (command.equals("r")) {
                removeQuestion();
            } else if (command.equals("f")) {
                processFinish();
                keepGoing = false;
            } else if (command.equals("v")) {
                displayQuizLibrary();
            } else {
                quizTool.displayGetNewStringInput(dialog);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: process a finish command
    private void processFinish() {
        quizTool.displayDialog("New quiz has been made!");
        int i = quizTool.yesOrNo("Do you want to save a quiz?");
        saveQuizLibrary(i);
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: Asks user to either save or don't save the quizLibrary to a file
    //          saves quizLibrary to a file if user enters "y", don't save otherwise
    public void saveQuizLibrary(int i) {
        if (i == 0) {
            try {
                jsonWriter.open();
                jsonWriter.write(quizLibrary);
                jsonWriter.close();
                System.out.println("Saved " + quizLibrary.getName() + "to " + JSON_STORE);
                makeQuizTool.printQuiz();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else {
            System.out.println("\nYour quiz is not saved to a file.");
            makeQuizTool.printQuiz();
        }
    }

    // MODIFIES: this
    // EFFECTS: make a question with the given question text, options, and a correct answer and store it in this quiz
    public void makeQuestion() {
        Question question;
        boolean keepGoing = true;
        ArrayList<String> questionMaterial = new ArrayList<>();

        getInputQuestion(questionMaterial);

        while (keepGoing) {
            String correctAnswer = quizTool.displayGetNewStringInput("Assign a correct answer (A or B or C or D): ");
            correctAnswer = correctAnswer.toUpperCase();
            if (correctAnswer.equals("A")
                    || correctAnswer.equals("B")
                    || correctAnswer.equals("C")
                    || correctAnswer.equals("D")) {
                question = new Question(questionMaterial.get(0), questionMaterial.get(1), questionMaterial.get(2),
                        questionMaterial.get(3), questionMaterial.get(4), correctAnswer);
                this.quiz.addQuestion(question);
                keepGoing = false;
            } else {
                quizTool.displayGetNewStringInput("Please enter a valid answer (A or B or C or D): ");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a question from the list of questions
    public void removeQuestion() {
        boolean keepGoing = true;

        if (quiz.getQuestions().isEmpty()) {
            quizTool.displayDialog("There is no question to remove");
        } else {
            while (keepGoing) {
                printQuiz(quiz);
                Integer numQuestionToRemove = quizTool.displayGetNewIntegerInput("Enter the question number: ");
                numQuestionToRemove -= 1;
                ArrayList<Question> questions = quiz.getQuestions();

                if (numQuestionToRemove >= questions.size() || numQuestionToRemove < 0) {
                    quizTool.displayDialog("There is no such question. Please enter a valid number: ");
                } else {
                    Question questionToRemove = questions.get(numQuestionToRemove);
                    quiz.removeQuestion(questionToRemove);
                    quizTool.displayDialog("Question has been removed.");
                    keepGoing = false;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allow user to choose a quiz from this quizLibrary and take the quiz
    private void takeQuiz() {
        if (quizLibrary.getQuizzes().isEmpty()) {
            quizTool.displayDialog("\nThere is no quiz in the Quiz Library. Please start by making a new quiz.");
        } else {
            takeQuizTool.printQuiz();
        }
    }

    // MODIFIES: this
    // EFFECTS: add user input for the question materials to the given questionMaterial list
    private void getInputQuestion(ArrayList<String> questionMaterial) {
        String text = quizTool.displayGetNewStringInput("Type your question: ");
        questionMaterial.add(text);
        String optionA = quizTool.displayGetNewStringInput("Type option A: ");
        questionMaterial.add(optionA);
        String optionB = quizTool.displayGetNewStringInput("Type option B: ");
        questionMaterial.add(optionB);
        String optionC = quizTool.displayGetNewStringInput("Type option C: ");
        questionMaterial.add(optionC);
        String optionD = quizTool.displayGetNewStringInput("Type option D: ");
        questionMaterial.add(optionD);
    }

    // EFFECTS: print out the given quiz
    private void printQuiz(Quiz quiz) {
        int questionNum = 0;
        for (Question q : quiz.getQuestions()) {
            questionNum += 1;
            System.out.println("Question" + questionNum + ": " + q.getText());
            System.out.println("OptionA:" + q.getOptionA());
            System.out.println("OptionB:" + q.getOptionB());
            System.out.println("OptionC:" + q.getOptionC());
            System.out.println("OptionD:" + q.getOptionD());
            System.out.println("Correct Answer:" + q.getAnswer());
        }
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads quizLibrary from file
    public QuizLibrary loadQuizLibrary() {
        try {
            quizLibrary = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        return quizLibrary;
    }

    // MODIFIES: this
    // EFFECTS: display a quiz library
    public void displayQuizLibrary() {
        makeQuizTool.printQuiz();
    }

    // MODIFIES: this
    // EFFECTS: sets out a JFrame with a given component
    public void initializeJFrame(Component c) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(c);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: prints out event log
    public void printLog(EventLog el) {
        for (Event event : el) {
            System.out.println(event.toString() + "\n\n");
        }
    }
}