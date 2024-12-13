package com.example.ekansgame.game;

import com.example.ekansgame.Game;
import com.example.ekansgame.Grafics;
import com.example.ekansgame.Screen;
import com.example.ekansgame.Input.TouchEvent;

import java.util.List;

public class ScreenMaxPunt extends Screen {
    String[] lineas = new String[5];

    public ScreenMaxPunt(Game game) {
        super(game);

        for (int i = 0; i < 5; i++) {
            lineas[i] = (i + 1) + ". " + Configurations.maxPuntuaciones[i];
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
                    if(Configurations.sonidoHabilitado)
                        Assetes.pulsar.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Grafics g = game.getGrafics();

        g.drawPixmap(Assetes.fondo, 0, 0);
        g.drawPixmap(Assetes.menuprincipal, 64, 20, 0, 42, 196, 42);

        int y = 100;
        for (int i = 0; i < 5; i++) {
            dibujarTexto(g, lineas[i], 20, y);
            y += 50;
        }

        g.drawPixmap(Assetes.botones, 0, 416, 64, 64, 64, 64);
    }

    public void dibujarTexto(Grafics g, String linea, int x, int y) {
        int len = linea.length();
        for (int i = 0; i < len; i++) {
            char character = linea.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assetes.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
