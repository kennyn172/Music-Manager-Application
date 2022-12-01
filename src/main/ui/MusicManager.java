package ui;

import model.Library;

import java.util.Scanner;


//Music Manager Application
public class MusicManager {

    private Library lib;
    private Scanner input;

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
    }

    private void createLibrary() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        lib = new Library();
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



}
