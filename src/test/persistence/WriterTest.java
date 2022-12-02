package persistence;

import model.Library;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//test methods adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class WriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Library lib = new Library();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            Library lib = new Library();
            JsonWriter writer = new JsonWriter("data/writerTestEmpty.json");
            writer.open();
            writer.write(lib);
            writer.close();

            JsonReader reader = new JsonReader("data/writerTestEmpty.json");
            lib = reader.read();

            assertEquals(0, lib.getSongs().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void writerTestLibrary() {
        try {
            Library lib = new Library();
            lib.addSong("O Canada", 2,"John Canada",true);
            lib.addSong("Happy Birthday", 3,"John Birthday",false);
            JsonWriter writer = new JsonWriter("data/writerTestLibrary.json");
            writer.open();
            writer.write(lib);
            writer.close();

            JsonReader reader = new JsonReader("data/writerTestLibrary.json");
            lib = reader.read();
            List<Song> songs = lib.getSongs();
            assertEquals(2, songs.size());
            checkSong("O Canada", 2,"John Canada",true, songs.get(0));
            checkSong("Happy Birthday", 3,"John Birthday",false, songs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
