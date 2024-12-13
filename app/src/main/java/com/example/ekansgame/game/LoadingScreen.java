package com.example.ekansgame.game;

import com.example.ekansgame.Game;
import com.example.ekansgame.Grafics;
import com.example.ekansgame.Screen;
import com.example.ekansgame.Grafics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Grafics g = game.getGrafics();
        Assetes.fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assetes.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assetes.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assetes.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assetes.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assetes.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assetes.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assetes.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assetes.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assetes.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assetes.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assetes.ocasoballarriba = g.newPixmap("barcoarriba.png", PixmapFormat.ARGB4444);
        Assetes.ocasoballizquierda = g.newPixmap("barcoizquierda.png", PixmapFormat.ARGB4444);
        Assetes.ocasoballabajo = g.newPixmap("barcoabajo.png", PixmapFormat.ARGB4444);
        Assetes.ocasoballderecha = g.newPixmap("barcoderecha.png", PixmapFormat.ARGB4444);
        Assetes.nestballabajo = g.newPixmap("tripulacion.png", PixmapFormat.ARGB4444);
        Assetes.bulbasur = g.newPixmap("botin1.png", PixmapFormat.ARGB4444);
        Assetes.charmander = g.newPixmap("botin2.png", PixmapFormat.ARGB4444);
        Assetes.squirtel = g.newPixmap("botin3.png", PixmapFormat.ARGB4444);
        Assetes.pulsar = game.getAudio().nuevoSound("pulsar.ogg");
        Assetes.ataque = game.getAudio().nuevoSound("ataque.ogg");
        Assetes.derrota = game.getAudio().nuevoSound("derrota.ogg");


        Configurations.cargar(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

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
