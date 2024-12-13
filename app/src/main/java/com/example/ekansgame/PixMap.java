package com.example.ekansgame;

import com.example.ekansgame.Grafics.PixmapFormat;

public interface PixMap {
    int getWidth();

    int getHeight();

    PixmapFormat getFormat();

    void dispose();
}
