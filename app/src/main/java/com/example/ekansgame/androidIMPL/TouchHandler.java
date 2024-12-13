package com.example.ekansgame.androidIMPL;

import android.view.View;

import com.example.ekansgame.Input;

import java.util.List;

public interface TouchHandler extends View.OnTouchListener {
    boolean isTouchDown(int pointer);

    int getTouchX(int pointer);
    int getTouchY(int pointer);

    List<Input.TouchEvent> getTouchEvents();
}
