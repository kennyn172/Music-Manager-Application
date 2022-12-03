package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of all songs added by the user to the application.
public class Library implements Writable {

    private ArrayList<Song> songs;      //List of songs in library


    public Library() {
        this.songs = new ArrayList<>();
    }

    //REQUIRES: length >= 0
    //MODIFIES: this
    //EFFECTS: A new song is added to the end of the playlist
    public void addSong(String name, int length, String singer, Boolean isExplicit) {
        this.songs.add(new Song(name,length,singer,isExplicit));
        Event e = new Event("Song '" + name + "' added");
        EventLog.getInstance().logEvent(e);
    }

    public void addJSong(Song song) {
        this.songs.add(song);
    }

    //REQUIRES: songNumber >= 0 and songNumber < this.songs.size()
    //MODIFIES: this
    //EFFECTS: A selected song is removed from the library
    public void removeSong(int songNumber) {
        String name = songs.get(songNumber).getName();
        this.songs.remove(songNumber);
        Event e = new Event("Song '" + name + "' removed");
        EventLog.getInstance().logEvent(e);
    }

    //REQUIRES: songOne and songTwo >= 0, songOne != songTwo, songOne and songTwo < this.songs.size()
    //MODIFIES: this
    //EFFECTS: Swaps the order of two chosen songs
    //method taken from https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html
    public void swapSong(int songOne, int songTwo) {
        Collections.swap(this.songs,songOne,songTwo);
    }

    //MODIFIES: this
    //EFFECTS: Randomizes the order of songs
    //method taken from https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html
    public void shuffleSongs() {
        Collections.shuffle(this.songs);
    }

    //EFFECTS: returns the total length of all songs in library
    public int getTotalLength() {
        int total = 0;
        for (int i = 0; i < this.songs.size(); i++) {
            total = total + this.songs.get(i).getLength();
        }
        return total;
    }

    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    //Method taken from JSONSerializationDemo at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("songs", songsToJson());
        return json;
    }

    //Method taken from JSONSerializationDemo at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

}
