package com.example.ekansgame.game;

import android.graphics.Color;

import com.example.ekansgame.Game;
import com.example.ekansgame.Grafics;
import com.example.ekansgame.PixMap;
import com.example.ekansgame.Screen;
import com.example.ekansgame.Input.TouchEvent;

import java.util.List;

public class ScreenGame extends Screen {
    enum Estadogame {
        Preparado,
        Ejecutandose,
        Pausado,
        Fingame
    }

    Estadogame estado = Estadogame.Preparado;
    World mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";

    public ScreenGame(Game game) {
        super(game);
        mundo = new World();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(estado == Estadogame.Preparado)
            updateReady(touchEvents);
        if(estado == Estadogame.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == Estadogame.Pausado)
            updatePaused(touchEvents);
        if(estado == Estadogame.Fingame)
            updateGameOver(touchEvents);

    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (!touchEvents.isEmpty())
            estado = Estadogame.Ejecutandose;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configurations.sonidoHabilitado)
                        Assetes.pulsar.play(1);
                    estado = Estadogame.Pausado;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    mundo.kantos.girarIzquierda();
                }
                if(event.x > 256 && event.y > 416) {
                    mundo.kantos.girarDerecha();
                }
            }
        }

        mundo.update(deltaTime);
        if(mundo.finalJuego) {
            if(Configurations.sonidoHabilitado)
                Assetes.derrota.play(1);
            estado = Estadogame.Fingame;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configurations.sonidoHabilitado)
                Assetes.ataque.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Configurations.sonidoHabilitado)
                            Assetes.pulsar.play(1);
                        estado = Estadogame.Ejecutandose;
                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Configurations.sonidoHabilitado)
                            Assetes.pulsar.play(1);
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
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

        if (g != null) { // Verifica que g no sea null
            g.drawPixmap(Assetes.fondo, 0, 0);
            drawWorld(mundo);
            if (estado == Estadogame.Preparado)
                drawReadyUI();
            if (estado == Estadogame.Ejecutandose)
                drawRunningUI();
            if (estado == Estadogame.Pausado)
                drawPausedUI();
            if (estado == Estadogame.Fingame)
                drawGameOverUI();

            drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length() * 20 / 2, g.getHeight() - 42);
        }
    }
    private void drawWorld(World mundo) {
        Grafics g = game.getGrafics();
        Kantos kantos = mundo.kantos;
        Pokeball head = kantos.partes.get(0);
        Pokemon pokemon = mundo.pokemon;

        PixMap stainPixmap = null;
        if(pokemon.tipo== Pokemon.TIPO_1)
            stainPixmap = Assetes.bulbasur;
        if(pokemon.tipo == Pokemon.TIPO_2)
            stainPixmap = Assetes.charmander;
        if(pokemon.tipo == Pokemon.TIPO_3)
            stainPixmap = Assetes.squirtel;
        int x = pokemon.x * 32;
        int y = pokemon.y * 32;
        g.drawPixmap(stainPixmap, x, y);

        int len = kantos.partes.size();
        for(int i = 1; i < len; i++) {
            Pokeball part = kantos.partes.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assetes.nestballabajo, x, y);
        }

        PixMap headPixmap = null;
        if(kantos.direccion == kantos.ARRIBA)
            headPixmap = Assetes.ocasoballarriba;
        if(kantos.direccion == kantos.IZQUIERDA)
            headPixmap = Assetes.ocasoballizquierda;
        if(kantos.direccion == kantos.ABAJO)
            headPixmap = Assetes.ocasoballabajo;
        if(kantos.direccion == kantos.DERECHA)
            headPixmap = Assetes.ocasoballderecha;
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;

        if (headPixmap != null) { // Verificación de null
            g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
        } else {
            System.err.println("headPixmap es null, no se puede dibujar."); // Opcional: para registro de error
        }
    }

    private void drawReadyUI() {
        Grafics g = game.getGrafics();
        g.drawPixmap(Assetes.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Grafics g = game.getGrafics();
        g.drawPixmap(Assetes.botones, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assetes.botones, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assetes.botones, 256, 416, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Grafics g = game.getGrafics();
        g.drawPixmap(Assetes.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Grafics g = game.getGrafics();
        g.drawPixmap(Assetes.finjuego, 62, 100);
        g.drawPixmap(Assetes.botones, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Grafics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

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
        if(estado == Estadogame.Ejecutandose)
            estado = Estadogame.Pausado;

        if(mundo.finalJuego) {
            Configurations.addScore(mundo.puntuacion);
            Configurations.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
