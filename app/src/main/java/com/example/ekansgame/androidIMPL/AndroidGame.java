package com.example.ekansgame.androidIMPL;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;

import com.example.ekansgame.Audio;
import com.example.ekansgame.FileIO;
import com.example.ekansgame.Game;
import com.example.ekansgame.Grafics;
import com.example.ekansgame.Input;

import com.example.ekansgame.R;
import com.example.ekansgame.Screen;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Scanner;

public abstract class AndroidGame extends Activity implements Game {
    private AndroidFastRenderview renderview;
    private Grafics grafics;
    private Audio audio;
    private Input input;
    private FileIO fileIO;
    private Screen screen;
    private WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mantener la Screen activa
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Determinar si la orientación es paisaje o retrato
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;

        // Crear el framebuffer
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        // Obtener dimensiones de Screen modernas usando DisplayMetrics
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float scaleX = (float) frameBufferWidth / metrics.widthPixels;
        float scaleY = (float) frameBufferHeight / metrics.heightPixels;

        // Inicializar componentes
        renderview = new AndroidFastRenderview(this, frameBuffer);
        grafics = new AndroidGrafics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this); // Pasar el contexto
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderview, scaleX, scaleY);
        screen = getStartScreen();

        // Establecer la vista de renderizado
        setContentView(renderview);

        // Configurar WakeLock con un nombre único
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "com.ldm.ejemplojuegopiratas:GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Adquirir WakeLock con un timeout
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire(10 * 60 * 1000L /* 10 minutos */);
        }
        screen.resume();
        renderview.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Liberar WakeLock
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        renderview.pause();
        screen.pause();

        if (isFinishing()) {
            screen.dispose();
        }
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Grafics getGrafics() {
        return grafics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen no debe ser null");
        }
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }
}
