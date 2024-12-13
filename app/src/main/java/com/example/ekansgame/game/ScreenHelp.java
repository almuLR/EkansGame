package com.example.ekansgame.game;

import com.example.ekansgame.Game;

import java.util.List;

import com.example.ekansgame.Grafics;
import com.example.ekansgame.Input.TouchEvent;
import com.example.ekansgame.Screen;

public class ScreenHelp extends Screen {

    public ScreenHelp(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    game.setScreen(new ScreenHelp2(game));
                    if(Configurations.sonidoHabilitado)
                        Assetes.pulsar.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Grafics g = game.getGrafics();
        g.drawPixmap(Assetes.fondo, 0, 0);
        g.drawPixmap(Assetes.ayuda1, 64, 100);
        g.drawPixmap(Assetes.botones, 256, 416, 0, 64, 64, 64);
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
