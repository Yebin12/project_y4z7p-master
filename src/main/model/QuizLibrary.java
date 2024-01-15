package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents list of quizzes stored in the quiz system
public class QuizLibrary implements Writable {
    private String name;
    private ArrayList<Quiz> quizzes;

    // EFFECTS: constructs a quizLibrary with the given name and the empty list of quizzes
    public QuizLibrary(String name) {
        quizzes = new ArrayList<>();
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: add new quiz to the quizzes list
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    // EFFECTS: return the list of quizzes in this quizLibrary
    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    // EFFECTS: return this QuizLibrary's name
    public String getName() {
        return name;
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("quizzes", quizzesToJson());
        return json;
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns quizzes in this quizLibrary as a JSON array
    private JSONArray quizzesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Quiz t : quizzes) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
