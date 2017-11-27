package com.monoton.horizont.bomb.painter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.monoton.horizont.bomb.painter.Constants;
import com.monoton.horizont.bomb.painter.movement.BallInBasketMovement;
import com.monoton.horizont.bomb.painter.movement.BallInBasketMovementFactory;
import com.monoton.horizont.bomb.painter.movement.LogarithmicSpiral;

public class BallInBasket extends Actor{

    private BallInBasketMovement inBasketMovement;
    private TextureRegion ballTexture;

    private float stateTime = 0f;
    private float animationTime = 1f;
    private float angle;
    private float dimension;

    public BallInBasket(TextureRegion ballTexture, float pointX, float pointY, float basketCenterX, float basketCenterY, float angle, float screenWidth, float screenHeight, float dimension) {
        this.ballTexture = ballTexture;
        this.setBounds(0, 0, screenWidth, screenHeight);
        this.inBasketMovement = BallInBasketMovementFactory.getMovement(Constants.IN_BASKET_MOVEMENT_LOGARITHMIC_SPIRAL,pointX,pointY,basketCenterX, basketCenterY,angle);
        this.angle = MathUtils.radiansToDegrees * angle;
        this.dimension = dimension;

    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        if(stateTime<animationTime){
            float ratio = stateTime / animationTime;
            Vector2 cartesianPoint = inBasketMovement.getCartesianPoint(ratio);//ratio*2*MathUtils.PI
            float currentDimension = (1-ratio)*dimension;
          /*  Color color = batch.getColor();
            batch.setColor(1,0,0,1);*/
            batch.draw(ballTexture, cartesianPoint.x - currentDimension / 2, cartesianPoint.y - currentDimension / 2, currentDimension / 2, currentDimension / 2, currentDimension, currentDimension, 1, 1, this.angle);
//            batch.setColor(color);

        }else {
            remove();
        }

    }
}