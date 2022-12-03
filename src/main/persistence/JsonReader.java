package persistence;

import model.Song;
import model.Library;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//represents a reader that reads JSON data from a file
//Methods taken from JSONSerializationDemo at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Library parseLibrary(JSONObject jsonObject) {
        Library lib = new Library();
        addSongs(lib,jsonObject);
        return lib;
    }

    private void addSongs(Library lib, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(lib, nextSong);
        }
    }

    private void addSong(Library lb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int length = jsonObject.getInt("length");
        String singer = jsonObject.getString("singer");
        boolean isExplicit = jsonObject.getBoolean("explicit?");
        Song song = new Song(name, length,singer,isExplicit);
        lb.addJSong(song);
    }

}
