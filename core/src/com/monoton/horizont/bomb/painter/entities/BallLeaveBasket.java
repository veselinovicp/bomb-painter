package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BallLeaveBasket extends Actor {

    private TextureRegion[] animationImages;

    float positionX,  positionY,  screenWidth,  screenHeight, dimension;

    private Animation animation;

    private float stateTime = 0f;

    public BallLeaveBasket(TextureRegion[] animationImages, float positionX, float positionY, float screenWidth, float screenHeight, float dimension) {
        this.animationImages = animationImages;
        this.positionX = positionX;
        this.positionY = positionY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.dimension = dimension;

        // Initialize the Animation with the frame interval and array of frames
        animation = new Animation<TextureRegion>(0.015f, animationImages);

        this.setBounds(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time



        if(!animation.isAnimationFinished(stateTime)){



            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = (TextureRegion)animation.getKeyFrame(stateTime, false);



            batch.draw(currentFrame, positionX-dimension/2f, positionY-dimension/2f,dimension,dimension);
        }else {

            remove();
        }







    }


}
