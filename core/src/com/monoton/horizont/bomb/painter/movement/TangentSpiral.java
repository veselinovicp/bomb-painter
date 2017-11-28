package com.monoton.horizont.bomb.painter.movement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by veselinovicp on 28.11.2017.
 */
public class TangentSpiral implements BallInBasketMovement {

    private float pointX, pointY;

    private float a,b;
    private float basketCenterX, basketCenterY;
    private float angle;
    private float linearVelocityAngleBeforeColision;
    private float T;
    private float fi;

    public TangentSpiral(float pointX, float pointY, float basketCenterX, float basketCenterY, float angle, float linearVelocityAngleBeforeColision) {
        this.pointX = basketCenterX-pointX;
        this.pointY = basketCenterY-pointY;

        this.basketCenterX = basketCenterX;
        this.basketCenterY = basketCenterY;
        this.angle = angle;
        this.linearVelocityAngleBeforeColision=linearVelocityAngleBeforeColision;
        this.angle=linearVelocityAngleBeforeColision+ MathUtils.PI;

        this.T = (float)Math.tan(linearVelocityAngleBeforeColision);
        this.fi = MathUtils.PI-MathUtils.atan2(pointY, pointX);


        calculateParameters();

    }

    private void calculateParameters() {

        float distance=(float)Math.sqrt(pointX*pointX+pointY*pointY);


        b = (fi*(MathUtils.cos(fi)+T*MathUtils.sin(fi)))/(T*MathUtils.cos(fi)-MathUtils.sin(fi));



        a = distance/(float)(Math.pow(fi,b));


        System.out.println("a: "+a+", b: "+b);
    }

    public Vector2 getCartesianPoint(float percent){
        float an = fi-percent*fi;
        float r = a * (float) Math.pow(an, b);
        float x = r * MathUtils.cos(an)+basketCenterX;//-pointX
        float y = r * MathUtils.sin(an)+basketCenterY;//-pointY
        return new Vector2(x,y);
    }

}
