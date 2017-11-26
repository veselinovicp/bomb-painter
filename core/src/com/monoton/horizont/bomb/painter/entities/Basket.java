package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Basket extends Actor {

    private TextureRegion basketTexture;
    private float dimension;
    private float centerX, centerY;
    private float screenWidth, screenHeight;

    public Basket(TextureRegion basketTexture, float dimension, float centerX, float centerY, float screenWidth, float screenHeight) {
        this.basketTexture = basketTexture;
        this.dimension = dimension;
        this.centerX = centerX;
        this.centerY = centerY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setBounds(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {



            batch.draw(basketTexture, centerX - dimension / 2, centerY - dimension / 2, dimension / 2, dimension / 2, dimension, dimension, 1, 1, 0);


    }
}
