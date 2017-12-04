package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.monoton.horizont.bomb.painter.Constants;
import com.monoton.horizont.bomb.painter.movement.BallInBasketMovement;
import com.monoton.horizont.bomb.painter.movement.BallInBasketMovementFactory;

public class BallInBasket extends Actor{

    private BallInBasketMovement inBasketMovement;
    private TextureRegion ballTexture;

    private float stateTime = 0f;
    private float animationTime = 2.197f;
    private float angle;
    private float dimension;
    private Sound score;


    private TextureRegion[] animationImages;

    private float screenWidth, screenHeight;
    private Stage stage;

    public BallInBasket(TextureRegion ballTexture, float pointX, float pointY, float basketCenterX, float basketCenterY, float angle, float screenWidth, float screenHeight, float dimension, float linearVelocityAngleBeforeColision, Sound score, TextureRegion[] animationImages, Stage stage) {
        this.ballTexture = ballTexture;
        this.setBounds(0, 0, screenWidth, screenHeight);
        this.inBasketMovement = BallInBasketMovementFactory.getMovement(Constants.IN_BASKET_MOVEMENT_TANGENT_SPIRAL,pointX,pointY,basketCenterX, basketCenterY,angle, linearVelocityAngleBeforeColision);
        this.angle = MathUtils.radiansToDegrees * angle;
        this.dimension = dimension;
        this.score=score;
        this.animationImages = animationImages;
        this.screenHeight=screenHeight;
        this.screenWidth =screenWidth;

        this.stage=stage;




    }

    private float lastDimension;
    private Vector2 lastCartesianPoint;




    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        if(stateTime<animationTime){
            float ratio = stateTime / animationTime;
            Vector2 cartesianPoint = inBasketMovement.getCartesianPoint(ratio);//ratio*2*MathUtils.PI
            float currentDimension = (1-ratio/3f)*dimension;

            batch.draw(ballTexture, cartesianPoint.x - currentDimension / 2, cartesianPoint.y - currentDimension / 2, currentDimension / 2, currentDimension / 2, currentDimension, currentDimension, 1, 1, this.angle);
            lastCartesianPoint=cartesianPoint;
            lastDimension = currentDimension;


        }else {
            score.play();
            stage.addActor(new BallLeaveBasket(animationImages,lastCartesianPoint.x, lastCartesianPoint.y, screenWidth, screenHeight, lastDimension));
            remove();
        }

    }


}
