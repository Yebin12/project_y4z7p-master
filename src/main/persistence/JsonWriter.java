package persistence;

import model.QuizLibrary;
import org.json.JSONObject;


import java.io.*;

// modeled based on: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes JSON representation of quizLibrary to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of quizLibrary to file
    public void write(QuizLibrary quizLibrary) {
        JSONObject json = quizLibrary.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
