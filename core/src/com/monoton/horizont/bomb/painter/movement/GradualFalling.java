package com.monoton.horizont.bomb.painter.movement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/**
 * r=a*fi(power b)
 */
public class GradualFalling implements BallInBasketMovement{

    private float pointX, pointY;


    private float basketCenterX, basketCenterY;
    private float angle;

    private Vector2 lastPoint;
    private static final float ANGLE_FACTOR=0.1f;
    private float initialDistanceToCenter;


    public GradualFalling(float pointX, float pointY, float basketCenterX, float basketCenterY, float angle) {
        this.pointX = pointX;
        this.pointY = pointY;

        this.basketCenterX = basketCenterX;
        this.basketCenterY = basketCenterY;
        this.angle = angle;




    }


    public Vector2 getCartesianPoint(float percent){
        if(lastPoint==null){
            Vector2 vector2 = new Vector2(pointX, pointY);
            lastPoint=vector2;
            initialDistanceToCenter = calculateDistanceToCenter(vector2);
            return vector2;
        }else {

            float desiredAngle = MathUtils.atan2(basketCenterY-lastPoint.y, basketCenterX-lastPoint.x);

            float angleDelta = angle-desiredAngle;
            float newAngle = ANGLE_FACTOR * angleDelta;
      /*      float distanceToCenter = calculateDistanceToCenter(lastPoint);
            float distanceFactor = 1 - (distanceToCenter / initialDistanceToCenter);
            float dx = (percent-distanceFactor)*(basketCenterX-pointX);
            float dy = (percent-distanceFactor)*(basketCenterY-pointY);*/
            float dx = 0.1f*MathUtils.cos(newAngle);
            float dy = 0.1f*MathUtils.sin(newAngle);

            Vector2 vector2 = new Vector2(lastPoint.x+dx, lastPoint.y+dy);
            lastPoint=vector2;
            return vector2;

        }

    }

    private float calculateDistanceToCenter(Vector2 point){
        return (float) Math.sqrt(point.x*point.x+point.y+point.y);

    }

}
