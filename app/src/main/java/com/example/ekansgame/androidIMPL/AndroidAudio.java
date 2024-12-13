package com.example.ekansgame.androidIMPL;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.ekansgame.Audio;
import com.example.ekansgame.Music;
import com.example.ekansgame.Sound;

import java.io.IOException;

public class AndroidAudio implements Audio {
    AssetManager assets;
    SoundPool soundPool;

    public AndroidAudio(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        this.assets = activity.getAssets();

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        this.soundPool = new SoundPool.Builder()
                .setMaxStreams(20) // Número máximo de streams
                .setAudioAttributes(audioAttributes) // Atributos de audio
                .build();
    }

    @Override
    public Music nuevaMusic(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new AndroidMusic(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("no se ha podido cargar archivo '" + filename + "'");
        }
    }

    @Override
    public Sound nuevoSound(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido cargar archivo '" + filename + "'");
        }
    }
}
