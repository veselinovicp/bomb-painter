package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ball extends Actor{

    private Body ballBody;
    private Texture ballTexture;
    private float dimension;

    public Ball(Body ballBody,Texture ballTexture,float dimension, float screenWidth, float screenHeight) {
        this.ballBody = ballBody;
        this.ballTexture = ballTexture;
        this.dimension=dimension;

        this.setBounds(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = ballBody.getPosition();

        batch.draw(ballTexture, position.x - dimension / 2f, position.y - dimension / 2f, dimension, dimension);

    }
}
