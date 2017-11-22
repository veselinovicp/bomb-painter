package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.monoton.horizont.bomb.painter.communication.ExplosionCallback;

public class Explosion extends Actor {

    private Array<Body> explosionsParticles;
    private  float screenWidth, screenHeight;
    private float positionX,  positionY;
    private ExplosionCallback callback;

    public Explosion(Array<Body> explosionsParticles, ExplosionCallback callback, float positionX, float positionY, float screenWidth, float screenHeight) {
        this.explosionsParticles = explosionsParticles;
        this.callback=callback;
        this.positionX=positionX;
        this.positionY=positionY;
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
        this.setBounds(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        System.out.println("exploding..");
        callback.explosionEnded(this);



    }

    public Array<Body> getExplosionsParticles() {
        return explosionsParticles;
    }
}
