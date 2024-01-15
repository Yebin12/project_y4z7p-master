package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents quiz with name and list of questions 
public class Quiz implements Writable {

    private String name;
    private ArrayList<Question> questions;

    // EFFECTS: Constructs a quiz object with quiz name and list of questions
    public Quiz(String name) {
        this.name = name;
        questions = new ArrayList<>();
    }

    // REQUIRES: no question should be duplicated
    // MODIFIES: this
    // EFFECTS: add the given question to the end of the list of questions of this quiz and log an event
    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
            EventLog.getInstance().logEvent(new Event("Added a question to a quiz"));
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the given question from the list of questions of this quiz and log an event
    public void removeQuestion(Question question) {
        questions.remove(question);
        EventLog.getInstance().logEvent(new Event("Removed a question from a quiz"));
    }

    // EFFECTS: returns this quiz's list of questions
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // EFFECTS: returns this quiz's name
    public String getQuizName() {
        return name;
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("questions", questionsToJson());
        return json;
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns questions in this quiz as JSON array
    private JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Question q : questions) {
            jsonArray.put(q.toJson());
        }

        return jsonArray;
    }

    @Override
    public String toString() {
        return name;
    }
}