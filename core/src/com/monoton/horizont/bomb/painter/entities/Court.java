package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.monoton.horizont.bomb.painter.Constants;

public class Court extends Actor {

    private float screenWidth, screenHeight;
    private Texture background;

    public Court(Texture background, float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.background = background;
//        this.setBounds(0, 0, screenWidth, screenHeight);
        this.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    //    this.setBounds(0, 0, screenWidth, screenHeight);

    //batch.draw(mTextureBg, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(background, 0 , 0, screenWidth, screenHeight);
        batch.draw(background,0,0,Gdx.graphics.getWidth()/ Constants.SCREEN_RATIO, Gdx.graphics.getHeight()/Constants.SCREEN_RATIO);

//        batch.draw(texture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
}
