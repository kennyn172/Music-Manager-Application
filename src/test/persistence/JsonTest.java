package persistence;

import model.Library;
import model.Song;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSong(String name, int length, String singer, boolean isExplicit, Song song) {
        assertEquals(name,song.getName());
        assertEquals(length,song.getLength());
        assertEquals(singer,song.getSingerName());
        assertEquals(isExplicit,song.getIsExplicit());
    }
}