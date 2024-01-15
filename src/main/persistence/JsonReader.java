package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Question;
import model.Quiz;
import model.QuizLibrary;
import org.json.*;

// modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads QuizLibrary from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads quizLibrary from file and returns it;
    // throws IOException if an error occurs reading data from file
    public QuizLibrary read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseQuizLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses quizLibrary from JSON object and returns it
    private QuizLibrary parseQuizLibrary(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        QuizLibrary quizLibrary = new QuizLibrary(name);
        addQuizzes(quizLibrary, jsonObject);
        return quizLibrary;
    }

    // MODIFIES: Quizlibrary
    // EFFECTS: parses quizzes from JSON object and adds them to QuizLibrary
    private void addQuizzes(QuizLibrary quizLibrary, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("quizzes");
        for (Object json : jsonArray) {
            JSONObject nextQuiz = (JSONObject) json;
            addQuiz(quizLibrary, nextQuiz);
        }
    }

    // MODIFIES: Quizlibrary
    // EFFECTS: parses quiz from JSON object and adds it to quizLibrary
    private void addQuiz(QuizLibrary quizLibrary, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Quiz quiz = new Quiz(name);
        quizLibrary.addQuiz(quiz);
        addQuestions(quiz, jsonObject);
    }

    // MODIFIES: quiz
    // Effects: parses questions from JSON object and adds them to quiz
    private void addQuestions(Quiz quiz, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        for (Object json : jsonArray) {
            JSONObject nextQuestion = (JSONObject) json;
            addQuestion(quiz, nextQuestion);
        }
    }

    // MODIFIES: quiz
    // EFFECTS: parses question from the jSON object and adds it to quiz
    private void addQuestion(Quiz quiz, JSONObject jsonObject) {
        String text = jsonObject.getString("text");
        String optionA = jsonObject.getString("optionA");
        String optionB = jsonObject.getString("optionB");
        String optionC = jsonObject.getString("optionC");
        String optionD = jsonObject.getString("optionD");
        String answer = jsonObject.getString("answer");
        Question question = new Question(text, optionA, optionB, optionC, optionD, answer);
        quiz.addQuestion(question);
    }
}
