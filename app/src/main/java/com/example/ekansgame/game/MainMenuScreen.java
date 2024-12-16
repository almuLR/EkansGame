package com.example.ekansgame.game;

import com.example.ekansgame.Game;
import com.example.ekansgame.Grafics;
import com.example.ekansgame.Screen;
import com.example.ekansgame.Input.TouchEvent;

import java.util.List;

public class MainMenuScreen extends Screen {

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Grafics g = game.getGrafics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Configurations.sonidoHabilitado = !Configurations.sonidoHabilitado;
                    if(Configurations.sonidoHabilitado)
                        Assetes.pulsar.play(1);
                }
                if(inBounds(event, 64, 220, 192, 42) ) {
                    game.setScreen(new ScreenGame(game));
                    if(Configurations.sonidoHabilitado)
                    {Assetes.pulsar.play(1);
                    }

                    return;
                }
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {
                    game.setScreen(new ScreenMaxPunt(game));
                    if(Configurations.sonidoHabilitado)
                        Assetes.pulsar.play(1);
                    return;
                }
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {
                    game.setScreen(new ScreenHelp(game));
                    if(Configurations.sonidoHabilitado)
                        Assetes.pulsar.play(1);
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        return event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1;
    }

    @Override
    public void present(float deltaTime) {
        Grafics g = game.getGrafics();

        g.drawPixmap(Assetes.fondo, 0, 0);
        g.drawPixmap(Assetes.logo, 32, 20);
        g.drawPixmap(Assetes.menuprincipal, 64, 220);
        if(Configurations.sonidoHabilitado)
            g.drawPixmap(Assetes.botones, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assetes.botones, 0, 416, 64, 0, 64, 64);
    }

    @Override
    public void pause() {
        Configurations.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
