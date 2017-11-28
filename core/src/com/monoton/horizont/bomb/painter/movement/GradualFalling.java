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
//    private static final float ANGLE_FACTOR=0.01f;
    private float initialDistanceToCenter;
    private float desiredAngle;
    private float linearVelocityAngleBeforeColision;



    public GradualFalling(float pointX, float pointY, float basketCenterX, float basketCenterY, float angle, float linearVelocityAngleBeforeColision) {
        this.pointX = pointX;
        this.pointY = pointY;

        this.basketCenterX = basketCenterX;
        this.basketCenterY = basketCenterY;
        this.angle = angle;
        this.linearVelocityAngleBeforeColision = linearVelocityAngleBeforeColision;

        initialDistanceToCenter = calculateDistanceToCenter(new Vector2(basketCenterX-pointX, basketCenterY-pointY));




    }


    public Vector2 getCartesianPoint(float percent){
        if(lastPoint==null){
            Vector2 vector2 = new Vector2(pointX, pointY);
            lastPoint=vector2;

            desiredAngle = MathUtils.atan2(basketCenterY-lastPoint.y, basketCenterX-lastPoint.x);
            return vector2;
        }else {


            float newAngle = (1-percent)*linearVelocityAngleBeforeColision + percent*desiredAngle;

            float x = pointX+initialDistanceToCenter*percent*MathUtils.cos(newAngle);//desiredAngle
            float y = pointY+initialDistanceToCenter*percent*MathUtils.sin(newAngle);//desiredAngle

            Vector2 vector2 = new Vector2(x, y);
            lastPoint=vector2;
            return vector2;

        }

    }

    private float calculateDistanceToCenter(Vector2 point){
        return (float) Math.sqrt(point.x*point.x+point.y+point.y);

    }

}
