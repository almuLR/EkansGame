package com.example.ekansgame.game;

import java.util.ArrayList;
import java.util.List;

public class Kantos {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;

    public List<Pokeball> partes = new ArrayList<>();
    public int direccion;

    public Kantos() {
        direccion = ARRIBA;
        partes.add(new Pokeball(5, 6));
        partes.add(new Pokeball(5, 7));
        partes.add(new Pokeball(5, 8));
    }

    public void girarIzquierda() {
        direccion += 1;
        if(direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if(direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void capturar() {
        Pokeball end = partes.get(partes.size()-1);
        partes.add(new Pokeball(end.x, end.y));
    }

    public void avance() {
        Pokeball ekans = partes.get(0);

        int len = partes.size() - 1;
        for(int i = len; i > 0; i--) {
            Pokeball antes = partes.get(i-1);
            Pokeball parte = partes.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if(direccion == ARRIBA)
            ekans.y -= 1;
        if(direccion == IZQUIERDA)
            ekans.x -= 1;
        if(direccion == ABAJO)
            ekans.y += 1;
        if(direccion == DERECHA)
            ekans.x += 1;

        if(ekans.x < 0)
            ekans.x = 9;
        if(ekans.x > 9)
            ekans.x = 0;
        if(ekans.y < 0)
            ekans.y = 12;
        if(ekans.y > 12)
            ekans.y = 0;
    }

    public boolean comprobarChoque() {
        int len = partes.size();
        Pokeball ekans = partes.get(0);
        for(int i = 1; i < len; i++) {
            Pokeball parte = partes.get(i);
            if(parte.x == ekans.x && parte.y == ekans.y)
                return true;
        }
        return false;
    }
}
