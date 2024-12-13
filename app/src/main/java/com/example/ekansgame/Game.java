package com.example.ekansgame;

public interface Game {
    Input getInput();

    FileIO getFileIO();

    Grafics getGrafics();

    Audio getAudio();

    void setScreen(Screen screen);

    Screen getCurrentScreen();

    Screen getStartScreen();
}
