package com.example.ekansgame;

public interface Music {

    public void play();
    public void stop();
    public void pause();

    public void setLooping(boolean looping);
    public void setVolume(float volume);

    boolean isPlaying();

    boolean isStopped();

    boolean isLooping();

    void dispose();
}
