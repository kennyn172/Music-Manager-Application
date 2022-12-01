package model;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    private Song testSong;

    @BeforeEach
    void runBefore() {
        testSong = new Song("O Canada",1,"John Canada",false);
    }

    @Test
    void testConstructor() {
        assertEquals("O Canada",testSong.getName());
        assertEquals(1,testSong.getLength());
        assertEquals("John Canada",testSong.getSingerName());
        assertEquals(false,testSong.getIsExplicit());
    }

}
