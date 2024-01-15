package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the question on the quiz with question text, options(A to D) and a correct answer
public class Question implements Writable {

    private String text;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;

    // EFFECTS: constructs a question object with text, 4 options (a to d), and an answer
    public Question(String text, String a, String b, String c, String d, String answer) {
        this.text = text;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;
        this.answer = answer;
    }

    // EFFECTS: return this question's text
    public String getText() {
        return this.text;
    }

    // EFFECTS: return this question's optionA
    public String getOptionA() {
        return this.optionA;
    }

    // EFFECTS: return this question's optionB
    public String getOptionB() {
        return this.optionB;
    }

    // EFFECTS: return this question's optionC
    public String getOptionC() {
        return this.optionC;
    }

    // EFFECTS: return this question's optionD
    public String getOptionD() {
        return this.optionD;
    }

    // EFFECTS: return the correct answer to this question
    public String getAnswer() {
        return this.answer;
    }

    // modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("text", text);
        json.put("optionA", optionA);
        json.put("optionB", optionB);
        json.put("optionC", optionC);
        json.put("optionD", optionD);
        json.put("answer", answer);

        return json;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><p align=left>");
        sb.append("Question: " + getText());
        sb.append("<br>");
        sb.append("OptionA)" + " " + getOptionA());
        sb.append("<br>");
        sb.append("OptionB)" + " " + getOptionB()); //should format
        sb.append("<br>");
        sb.append("OptionC)" + " " + getOptionC());
        sb.append("<br>");
        sb.append("OptionD)" + " " + getOptionD());
        sb.append("</p></html>");

        return sb.toString();
    }
}
