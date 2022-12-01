package model;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library testLib;

    @BeforeEach
    void runBefore() {
        testLib = new Library();
    }

    @Test
    void testConstructor() {
        assertEquals(0,testLib.getSongs().size());
    }

    @Test
    void testAddSong() {
        testLib.addSong("O Canada",1,"John Canada",false);
        assertEquals("O Canada",testLib.getSongs().get(0).getName());
        assertEquals(1, testLib.getSongs().get(0).getLength());
        assertEquals("John Canada",testLib.getSongs().get(0).getSingerName());
        assertEquals(false,testLib.getSongs().get(0).getIsExplicit());
    }

    @Test
    void testRemoveSong() {
        testLib.addSong("O Canada",1,"John Canada",false);
        testLib.addSong("Happy Birthday",2,"Unknown",false);
        testLib.removeSong(0);
        assertEquals(1,testLib.getSongs().size());
    }

    @Test
    void testSwapSong() {
        testLib.addSong("O Canada",1,"John Canada",false);
        testLib.addSong("Happy Birthday",2,"Unknown",false);
        testLib.swapSong(0,1);
        assertEquals("Happy Birthday",testLib.getSongs().get(0).getName());
    }

    @Test
    void testTotalLength() {
        testLib.addSong("O Canada",1,"John Canada",false);
        testLib.addSong("Happy Birthday",2,"Unknown",false);
        testLib.addSong("Song 3",15,"John",true);
        assertEquals(18,testLib.getTotalLength());
    }
}