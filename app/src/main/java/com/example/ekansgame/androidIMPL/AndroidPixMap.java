package com.example.ekansgame.androidIMPL;

import android.graphics.Bitmap;

import com.example.ekansgame.PixMap;
import com.example.ekansgame.Grafics.PixmapFormat;

public class AndroidPixMap implements PixMap {

    Bitmap bitmap;
    PixmapFormat format;

    public AndroidPixMap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
