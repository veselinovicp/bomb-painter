package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.monoton.horizont.bomb.painter.communication.ExplosionCallback;

public class Explosion extends Actor {

    private Array<Body> explosionsParticles;

    private float positionX,  positionY;
    private ExplosionCallback callback;

    private float stateTime = 0f;
    private Animation animation;
    private float eplosionWidth=32;
    private float explostionHeight=32;
    private float eplosionParticleWidth=eplosionWidth/10f;
    private float explostionParticleHeight=explostionHeight/10f;


    public Explosion(Array<Body> explosionsParticles, ExplosionCallback callback, TextureRegion[] explosionImages, float positionX, float positionY, float screenWidth, float screenHeight) {
        this.explosionsParticles = explosionsParticles;
        this.callback=callback;

        // Initialize the Animation with the frame interval and array of frames
        animation = new Animation<TextureRegion>(0.015f, explosionImages);

        this.positionX=positionX;
        this.positionY=positionY;

        this.setBounds(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time



        if(!animation.isAnimationFinished(stateTime)){



            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = (TextureRegion)animation.getKeyFrame(stateTime, false);

            for(Body explosionParticle : explosionsParticles) {

                    Vector2 explosionParticlePosition = explosionParticle.getPosition();
                    batch.draw(currentFrame, explosionParticlePosition.x - eplosionParticleWidth / 2f, explosionParticlePosition.y - explostionParticleHeight / 2f, eplosionParticleWidth, explostionParticleHeight);

            }


            batch.draw(currentFrame, positionX-eplosionWidth/2f, positionY-explostionHeight/2f,eplosionWidth,explostionHeight);
        }else {
            callback.explosionEnded(this);
        }







    }

    public Array<Body> getExplosionsParticles() {
        return explosionsParticles;
    }
}
