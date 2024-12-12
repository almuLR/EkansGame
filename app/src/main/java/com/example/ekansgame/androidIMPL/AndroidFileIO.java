package com.example.ekansgame.androidIMPL;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.ekansgame.FileIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class AndroidFileIO implements FileIO {

    private final AssetManager assets;
    private final  String rutaAlmacenamientoExterno;

    public AndroidFileIO(Context context){
        this.assets = context.getAssets();

        if(context.getExternalFilesDir(null) != null){
            this.rutaAlmacenamientoExterno = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath();
        }else{
            throw new IllegalStateException("No se pudo acceder al almacenamiento externo");
        }
    }

    @Override
    public InputStream leerAsset(String nombreArchivo) throws IOException{
        return assets.open(nombreArchivo);
    }

    @Override
    public InputStream leerArchivo(String nombreArchivo) throws IOException {
        Path path = Paths.get(rutaAlmacenamientoExterno, nombreArchivo);

        // Verificar si el archivo existe antes de intentar leerlo
        if (!Files.exists(path)) {
            throw new IOException("El archivo no existe: " + path);
        }

        return Files.newInputStream(path);
    }

    @Override
    public OutputStream escribirArchivo(String nombreArchivo) throws IOException {
        Path path = Paths.get(rutaAlmacenamientoExterno, nombreArchivo);

        // Crear los directorios necesarios si no existen
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        return Files.newOutputStream(path);
    }

}