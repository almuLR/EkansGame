package com.example.ekansgame.androidIMPL;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.ekansgame.Input;

import java.util.List;

public class AndroidInput implements Input {
    AccMeterHandler accelHandler;
    KeyBoardHandler keyHandler;
    TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AccMeterHandler(context);
        keyHandler = new KeyBoardHandler(view);
        touchHandler = new MultTouchHandler(view, scaleX, scaleY);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

    @Override
    @NonNull
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }

    @Override
    @NonNull
    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }
}
