package com.example.ekansgame.game;

import com.example.ekansgame.Screen;
import com.example.ekansgame.androidIMPL.AndroidGame;

public class GameEkans extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
