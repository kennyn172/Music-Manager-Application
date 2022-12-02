package persistence;

import model.Library;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//test methods adapted from ReaderTest in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class ReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("data/fakefile.json");
        try {
            Library lib = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLibrary() {
        JsonReader reader = new JsonReader("data/readerTestEmpty.json");
        try {
            Library lib = reader.read();
            assertEquals(0, lib.getSongs().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSampleLibrary() {
        JsonReader reader = new JsonReader("data/readerTestSample.json");
        try {
            Library lib = reader.read();
            List<Song> songs = lib.getSongs();
            assertEquals(2, songs.size());
            checkSong("O Canada", 2,"John Canada",true, songs.get(0));
            checkSong("Happy Birthday", 2,"John Birthday",false, songs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}