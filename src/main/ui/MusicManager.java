package ui;

import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


//Music Manager Application
public class MusicManager {

    private static final String JSON_STORE = "data/Library.json";

    private Library lib;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //EFFECTS: runs the music manager application
    public MusicManager() {
        runApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user input, quits application
    //Citation: method was taken from TellerApp in https://github.students.cs.ubc.ca/CPSC210/TellerApp
    private void runApp() {
        boolean running = true;
        String command;

        createLibrary();

        while (running) {
            showSongs();
            showOptions();
            command = input.next();

            if (command.equals("quit")) {
                running = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nApplication closed, have a nice day!");
    }

    //MODIFIES: this
    //EFFECTS: displays a list of songs currently in the library, and total length of songs
    private void showSongs() {

        System.out.println("\nHere are your current songs:");
        if (lib.getSongs().isEmpty()) {
            System.out.println("Song library is currently empty");
        } else {
            for (int i = 0; i < lib.getSongs().size(); i++) {
                System.out.println(i + ". " + lib.getSongs().get(i).getName());
            }
            System.out.println("Total length of library: " + lib.getTotalLength() + " seconds");
        }
    }

    //EFFECTS: Displays options that can be performed by user
    private void showOptions() {
        System.out.println("\nUsing numbers, select one of the following actions or type quit:");
        System.out.println("1. Add song");
        System.out.println("2. Remove song");
        System.out.println("3. View song info");
        System.out.println("4. Swap song order");
        System.out.println("5. Shuffle playlist");
        System.out.println("6. Load Library from file");
        System.out.println("7. Save Library to file");
    }

    private void createLibrary() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        lib = new Library();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }


    //method taken from https://github.students.cs.ubc.ca/CPSC210/TellerApp
    private void processCommand(String command) {
        if (command.equals("1")) {
            doAddSong();
        } else if (command.equals("2")) {
            doRemoveSong();
        } else if (command.equals("3")) {
            viewSong();
        } else if (command.equals("4")) {
            swapSongOrder();
        } else if (command.equals("5")) {
            shufflePlaylist();
        } else if (command.equals("6")) {
            loadLibrary();
        } else if (command.equals("7")) {
            saveLibrary();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void doAddSong() {
        String songName;
        int songLength;
        String singer;
        boolean isExplicit;

        System.out.println("Song name?");
        songName = input.next();

        System.out.println("Song length in seconds?");
        songLength = input.nextInt();

        System.out.println("Song creator?");
        singer = input.next();

        System.out.println("Is this song explicit? (true or false)");
        isExplicit = input.nextBoolean();

        System.out.println("Song added.");
        if (songLength < 0) {
            songLength = 0;
        }
        lib.addSong(songName,songLength,singer,isExplicit);
    }

    private void doRemoveSong() {
        int songNumber;
        System.out.println("Number of song to be removed?");
        songNumber = input.nextInt();
        if (songNumber >= lib.getSongs().size() || songNumber < 0) {
            System.out.println("\nError, this song does not exist");
        } else {
            System.out.println("\nSong removed.");
            lib.removeSong(songNumber);
        }

    }

    private void viewSong() {
        int songNumber;
        System.out.println("Number of song to be viewed?");
        songNumber = input.nextInt();
        if (songNumber >= lib.getSongs().size() || songNumber < 0) {
            System.out.println("\nError, this song does not exist");
        } else {
            System.out.println("\nSong Information:");
            System.out.println("Song name: " + lib.getSongs().get(songNumber).getName());
            System.out.println("Song length: " + lib.getSongs().get(songNumber).getLength() + " seconds.");
            System.out.println("Artist: " + lib.getSongs().get(songNumber).getSingerName());
            if (lib.getSongs().get(songNumber).getIsExplicit()) {
                System.out.println("Is this song explicit? Yes.");
            } else {
                System.out.println("Is this song explicit? No.");
            }
        }
    }

    private void swapSongOrder() {
        int songOne;
        int songTwo;
        System.out.println("Enter the number of the first song");
        songOne = input.nextInt();
        System.out.println("Enter the number of the second song: ");
        songTwo = input.nextInt();
        if (songOne >= 0 && songTwo >= 0 && songTwo != songOne && songOne < lib.getSongs().size()
                && songTwo < lib.getSongs().size()) {
            lib.swapSong(songOne,songTwo);
            System.out.println("Song order has been swapped.");
        } else {
            System.out.println("Error, songs cannot be swapped.");
        }
    }

    private void shufflePlaylist() {
        lib.shuffleSongs();
    }

    ////Methods taken from JSONSerializationDemo at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadLibrary() {
        try {
            lib = jsonReader.read();
            System.out.println("Loaded Library from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    ////Methods taken from JSONSerializationDemo at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(lib);
            jsonWriter.close();
            System.out.println("Saved Library to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

}
