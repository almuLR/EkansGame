package com.example.ekansgame.game;

import java.util.Random;

public class World {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public Kantos kantos;
    public Pokemon pokemon;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean[][] campos = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public World() {
        kantos = new Kantos();
        colocarPokemon();
    }

    private void colocarPokemon() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = kantos.partes.size();
        for (int i = 0; i < len; i++) {
            Pokeball parte = kantos.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int pokemonX = random.nextInt(MUNDO_ANCHO);
        int pokemonY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (!campos[pokemonX][pokemonY])
                break;
            pokemonX += 1;
            if (pokemonX >= MUNDO_ANCHO) {
                pokemonX = 0;
                pokemonY += 1;
                if (pokemonY >= MUNDO_ALTO) {
                    pokemonY = 0;
                }
            }
        }
        pokemon = new Pokemon(pokemonX, pokemonY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (finalJuego)

            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            kantos.avance();

            Pokeball head = kantos.partes.get(0);
            if (head.x < 0 || head.x >= MUNDO_ANCHO || head.y < 0 || head.y >= MUNDO_ALTO){
                finalJuego = true;
                return;
            }

            if (kantos.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            if (head.x == pokemon.x && head.y == pokemon.y) {
                //puntuacion += INCREMENTO_PUNTUACION;
                switch (pokemon.tipo) {
                    case 0: // Bulbasur
                        puntuacion += 10;
                        break;
                    case 1: // Charmander
                        puntuacion += 20;
                        break;
                    case 2: // Squirtel
                        puntuacion += 30;
                        break;
                }
                kantos.capturar();
                if (kantos.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarPokemon();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}
