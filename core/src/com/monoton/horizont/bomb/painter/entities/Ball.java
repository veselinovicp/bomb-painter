package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.UUID;

public class Ball extends Actor{

    private String id;

    private Body ballBody;
    private TextureRegion ballTexture;
    private float dimension;

    public Ball(Body ballBody,TextureRegion ballTexture,float dimension, float screenWidth, float screenHeight) {
        this.ballBody = ballBody;
        this.ballTexture = ballTexture;
        this.dimension=dimension;

        this.setBounds(0, 0, screenWidth, screenHeight);

        id = UUID.randomUUID().toString();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(ballBody.isActive()) {
            Vector2 position = ballBody.getPosition();


            float angle = MathUtils.radiansToDegrees * ballBody.getAngle();


            batch.draw(ballTexture, position.x - dimension / 2, position.y - dimension / 2, dimension / 2, dimension / 2, dimension, dimension, 1, 1, angle);
        }else {
            remove();
        }

    }

    public String getId() {
        return id;
    }
}
