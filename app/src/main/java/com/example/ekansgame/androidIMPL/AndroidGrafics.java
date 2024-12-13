package com.example.ekansgame.androidIMPL;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.ekansgame.Grafics;
import com.example.ekansgame.PixMap;

import java.io.IOException;
import java.io.InputStream;

public class AndroidGrafics implements Grafics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGrafics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public PixMap newPixmap(String fileName, PixmapFormat format) {
        Bitmap.Config config;
        if (format == PixmapFormat.RGB565)
            config = Bitmap.Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Bitmap.Config.ARGB_4444;
        else
            config = Bitmap.Config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("No se ha podido cargar bitmap desde asset '" + fileName + "'");
        } catch (IOException e) {
            Log.e("AndroidGraficos", "Error al cargar el bitmap desde los assets: " + fileName, e);
            throw new RuntimeException("No se ha podido cargar bitmap desde asset '" + fileName + "'", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.w("AndroidGraficos", "Error al cerrar el flujo de entrada para: " + fileName, e);
                }
            }
        }
        if (bitmap.getConfig() == Bitmap.Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Bitmap.Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixMap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    @Override
    public void drawPixmap(PixMap pixmap, int x, int y, int srcX, int srcY,
                           int srcWidth, int srcHeight) {

        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((AndroidPixMap) pixmap).bitmap, srcRect, dstRect,
                null);
    }

    @Override
    public void drawPixmap(PixMap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixMap)pixmap).bitmap, x, y, null);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
