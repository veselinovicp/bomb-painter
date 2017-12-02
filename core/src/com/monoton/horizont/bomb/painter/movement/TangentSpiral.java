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
    private float originalPointX, originalPointY;
    private float k,n;


    public TangentSpiral(float _pointX, float _pointY, float basketCenterX, float basketCenterY, float angle, float linearVelocityAngleBeforeColision) {
        this.basketCenterX = basketCenterX;
        this.basketCenterY = basketCenterY;
        this.angle = angle;
        this.linearVelocityAngleBeforeColision=linearVelocityAngleBeforeColision;

        this.pointX = _pointX-basketCenterX;
        this.pointY = _pointY-basketCenterY;

        this.originalPointX = _pointX;
        this.originalPointY = _pointY;



        calculateParameters();

    }

    private void calculateParameters() {





        this.T = (float)MathUtils.sin(linearVelocityAngleBeforeColision)/MathUtils.cos(linearVelocityAngleBeforeColision);


        this.fi = MathUtils.atan2(pointY, pointX)+2*MathUtils.PI;


        float distance=(float)Math.sqrt(pointX*pointX+pointY*pointY);


        b = (fi*(MathUtils.cos(fi)+T*MathUtils.sin(fi)))/(T*MathUtils.cos(fi)-MathUtils.sin(fi));

            a = distance/(float)(Math.pow(fi,b));







        System.out.println("a: "+a+", b: "+b);
        float fiDeg = fi * MathUtils.radiansToDegrees;
        float velDeg = linearVelocityAngleBeforeColision * MathUtils.radiansToDegrees;

        System.out.println("pointX: "+pointX+", pointY: "+pointY);
        System.out.println("angle: "+fiDeg+", tangent: "+velDeg);

        if(isLinear()){
            calculateLinearFunction();
            System.out.println("linear function");
        }


    }

    private boolean isLinear(){
        return Float.isInfinite(a) || a==0;
    }

    private void calculateLinearFunction(){
        this.k = pointY/pointX;
        this.n = basketCenterY - k*basketCenterX;
    }

    public Vector2 getCartesianPoint(float percent){

        if(isLinear()){
            return getLinearPoint(percent);
        }
        float an = 0;
        if(b>0) {
            an = fi - percent * fi;
        }else {
            an = fi + percent * fi;
        }
        float r = a * (float) Math.pow(an, b);


        float x = r * MathUtils.cos(an)+basketCenterX;//-pointX
        float y = r * MathUtils.sin(an)+basketCenterY;//-pointY
        return new Vector2(x,y);
    }


    private Vector2 getLinearPoint(float percent){
        float x = originalPointX - percent * pointX;
        float y = k*x+n;

        return new Vector2(x,y);

    }

}
