package ui;

import model.Event;
import model.EventLog;
import model.Library;
import model.Song;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import java.awt.*;


//Music Manager Application
public class MusicManager extends JFrame implements ListSelectionListener, WindowListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;

    private static final String JSON_STORE = "data/Library.json";

    private Library lib;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JList list;
    private DefaultListModel listSongs;

    private JTextField songName;
    private JTextField songLength;
    private JTextField songArtist;
    private JTextField songE;

    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton swapButton;
    private JButton shuffleButton;
    private JButton loadButton;
    private JButton saveButton;


    //EFFECTS: runs the music manager application
    //CITATION: swing methods adapted from ListDemo at
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    public MusicManager() {
        super("Music Manager");
        createLibrary();
        setLayout(new GridLayout(2, 1));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(this);
        setVisible(true);

        listSongs = new DefaultListModel();
        ArrayList<Song> allSongs = lib.getSongs();
        for (int i = 0; i < lib.getSongs().size(); i++) {
            listSongs.addElement(lib.getSongs().get(i));
        }
        list = new JList(listSongs);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);

        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane,BorderLayout.CENTER);

        makeTextFields();
        makeButtons();
        pack();
    }

    private void makeTextFields() {
        AddListener addListener = new AddListener(addButton);
        songName = new JTextField("Enter Song Name here", 8);
        songName.addActionListener(addListener);
        songName.getDocument().addDocumentListener(addListener);
        songLength = new JTextField("Enter song length in seconds (numbers only)", 8);
        songLength.addActionListener(addListener);
        songLength.getDocument().addDocumentListener(addListener);
        songArtist = new JTextField("Enter artist name", 8);
        songArtist.addActionListener(addListener);
        songArtist.getDocument().addDocumentListener(addListener);
        songE = new JTextField("Is song explicit? (true or false)", 8);
        songE.addActionListener(addListener);
        songE.getDocument().addDocumentListener(addListener);
    }

    private void makeButtons() {
        AddListener addListener = new AddListener(addButton);
        RemoveListener removeListener = new RemoveListener(removeButton);
        ViewListener viewListener = new ViewListener(viewButton);
        ShuffleListener shuffleListener = new ShuffleListener(shuffleButton);
        SaveListener saveListener = new SaveListener(saveButton);
        LoadListener loadListener = new LoadListener(loadButton);
        addButton = new JButton("Add Song");
        addButton.addActionListener(addListener);
        removeButton = new JButton("Remove Song");
        removeButton.addActionListener(removeListener);
        viewButton = new JButton("View info");
        viewButton.addActionListener(viewListener);
        swapButton = new JButton("Swap songs (feature coming soon)");
        shuffleButton = new JButton("Shuffle Songs");
        shuffleButton.addActionListener(shuffleListener);
        loadButton = new JButton("Load Song Library from file");
        loadButton.addActionListener(loadListener);
        saveButton = new JButton("Save Song Library to file");
        saveButton.addActionListener(saveListener);

        makeButtonPane();
    }

    private void makeButtonPane() {
        //buttonpane for buttons and text fields
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(11, 1));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //text entry fields for adding songs
        buttonPane.add(songName);
        buttonPane.add(songLength);
        buttonPane.add(songArtist);
        buttonPane.add(songE);
        //buttons
        buttonPane.add(addButton);
        buttonPane.add(removeButton);
        buttonPane.add(viewButton);
        buttonPane.add(swapButton);
        buttonPane.add(shuffleButton);
        buttonPane.add(loadButton);
        buttonPane.add(saveButton);
        removeButton.setEnabled(false);
        viewButton.setEnabled(false);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == true) {

            if (list.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
                viewButton.setEnabled(false);
                shuffleButton.setEnabled(false);
            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
                viewButton.setEnabled(true);
                shuffleButton.setEnabled(true);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    //prints EventLog
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println("\n" + event.toString());
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = true;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = songName.getText();
            String length = songLength.getText();
            String artist = songArtist.getText();
            String explicit = songE.getText();


            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listSongs.addElement(songName.getText());
            lib.addSong(name,Integer.parseInt(length),artist,Boolean.parseBoolean(explicit));

            songName.requestFocusInWindow();
            songName.setText("");
            songLength.setText("");
            songArtist.setText("");
            songE.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
            removeButton.setEnabled(true);
            viewButton.setEnabled(true);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                addButton.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                addButton.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    class RemoveListener implements ActionListener {
        private JButton button;
        private boolean alreadyEnabled = false;

        public RemoveListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listSongs.remove(index);
            lib.removeSong(index);
            int size = listSongs.getSize();
            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == listSongs.getSize()) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }

    }

    class ViewListener extends JFrame implements ActionListener {
        private JButton button;

        private boolean alreadyEnabled = false;

        public ViewListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int size = listSongs.getSize();
            if (size == 0) {
                viewButton.setEnabled(false);
            }
            JLabel info = new JLabel("Song information:");
            JLabel name = new JLabel("Name: " + lib.getSongs().get(index).getName());
            JLabel length = new JLabel("Length: " + lib.getSongs().get(index).getLength() + " seconds");
            JLabel artist = new JLabel("Artist: " + lib.getSongs().get(index).getSingerName());
            JLabel explicit = new JLabel("Explicit? " + lib.getSongs().get(index).getIsExplicit().toString());
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5,1));
            panel.add(info);
            panel.add(name);
            panel.add(length);
            panel.add(artist);
            panel.add(explicit);
            add(panel);
            setMinimumSize(new Dimension(400, 400));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            pack();
            setVisible(true);
        }
    }



    class ShuffleListener implements ActionListener {
        private JButton button;

        public ShuffleListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            shufflePlaylist();
            listSongs.removeAllElements();
            for (int i = 0; i < lib.getSongs().size(); i++) {
                listSongs.addElement(lib.getSongs().get(i).getName());
            }
            list.setSelectedIndex(0);
        }
    }

    class LoadListener implements ActionListener {
        private JButton button;

        public LoadListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            loadLibrary();
            listSongs.removeAllElements();
            for (int i = 0; i < lib.getSongs().size(); i++) {
                listSongs.addElement(lib.getSongs().get(i).getName());
            }
            list.setSelectedIndex(0);
            if (listSongs.getSize() > 0) {
                viewButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        }

    }

    class SaveListener implements ActionListener {
        private JButton button;

        public SaveListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            saveLibrary();
        }
    }

    private void createLibrary() {
        lib = new Library();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
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
