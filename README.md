# My Personal Project

## Music manager application

What this application will *actually* do:
- Create multiple playlists, each playlist composed of individual songs
- Allow the user to shuffle the order of songs in the playlist, or change them manually
- Store song information, such as artist, length, album pictures, lyrics etc.
- Mark songs as explicit. 
- Allows users to manually add songs, which can then be added to playlists. 
- Specifically allow users to add multiple songs to a playlist at once.
- Customizable playlist names and icons.


This project will be designed like an alternative to something like Spotify
or iTunes, for people who want to listen to music. As someone
who frequently uses Spotify, the fact that I can't add multiple
songs to a playlist at once annoys me. **Therefore**, I will make sure
that this project allows the user to do so. If I have time, I
may also attempt to make the application actually be able tp 
play songs using mp3 files, but I'm not sure about the feasibility of 
this just yet.

## User Stories

- As a user, I want to be able to view a list of all songs in my library.
- As a user, I want to be able to add songs to my library.
- As a user, I want to change the order of songs in my library, either by shuffling or changing them manually.
- As a user, I want to view song information, such as name, length, etc. 
- As a user, I want to be able to tell if a song is explicit.
- As a user, I want to be able to remove songs.
- As a user, I want to view the total length of all my songs.
- As a user, I want to be able to save my list of songs to a file.
- As a user, I want to be able to load songs from a file.


## Instructions for Grader

- For first event: fill in the information for a song in the four text fields (Song name, song length (integer), artist name, and "true" of the song is deemed explicit)

- Then, click the Add Song button. This will add that song to the playlist.
- For the second event: After adding a song, select any added song and click the Remove Song button. This will remove the selected song from the playlist.
- Other possible events include viewing the information of a selected song or shuffling the songs
- You can save the state of the application by clicking the Save Song Library to file button.
- You can load the state of the application by clicking the Load Song Library from file button.