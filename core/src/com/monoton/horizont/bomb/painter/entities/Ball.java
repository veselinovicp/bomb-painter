package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.monoton.horizont.bomb.painter.logic.BodyDescription;

import java.util.UUID;

public class Ball extends Actor{

    private String id;

    private Body ballBody;
    private TextureRegion ballTexture;
    private float dimension;
    private float  basketCenterX, basketCenterY;
    private float screenWidth, screenHeight;
    private Stage particles;
    private Sound score;

    private TextureRegion[] animationImages;

    public Ball(Body ballBody, TextureRegion ballTexture, float dimension, float screenWidth, float screenHeight, float basketCenterX, float basketCenterY, Stage particles, Sound score, TextureRegion[] animationImages) {
        this.ballBody = ballBody;
        this.ballTexture = ballTexture;
        this.dimension=dimension;

        this.setBounds(0, 0, screenWidth, screenHeight);

        id = UUID.randomUUID().toString();
        this.basketCenterX=basketCenterX;
        this.basketCenterY=basketCenterY;
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
        this.particles =particles;
        this.score = score;
        this.animationImages = animationImages;
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

    @Override
    public boolean remove(){
        Vector2 position = ballBody.getPosition();
        float angle = ballBody.getLinearVelocity().angleRad();

        float angularVelocity = ballBody.getAngularVelocity();


        BodyDescription userData = (BodyDescription) ballBody.getUserData();
        float linearVelocityAngleBeforeColision = userData.getLinearVelocityAngleBeforeColision();

        BallInBasket ballInBasket = new BallInBasket(ballTexture, position.x, position.y, basketCenterX, basketCenterY, angle, screenWidth, screenHeight, dimension, linearVelocityAngleBeforeColision, score, animationImages, particles, angularVelocity);//ballBody.getAngle()
        particles.addActor(ballInBasket);
        return super.remove();
    }



    public String getId() {
        return id;
    }
}
