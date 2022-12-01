package model;

// Represents a song that has a name, length, and the name of the singer.
public class Song {
    private String name;            // Name of the song
    private int length;         // Length of the song in seconds
    private String singerName;      // Name of the singer
    private Boolean isExplicit;     // Whether the song is explicit


    //REQUIRES: length >= 0
    //EFFECTS: Song name, length, singer, and isExplicit are set
    public Song(String songName, int songLength, String singerName, Boolean isExplicit) {
        name = songName;
        length = songLength;
        this.singerName = singerName;
        this.isExplicit = isExplicit;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public String getSingerName() {
        return singerName;
    }

    public Boolean getIsExplicit() {
        return isExplicit;
    }



}
