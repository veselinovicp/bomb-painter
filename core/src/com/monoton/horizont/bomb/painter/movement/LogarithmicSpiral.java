package com.monoton.horizont.bomb.painter.movement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


/**
 * r=a*E(power (b*fi))
 * http://mathworld.wolfram.com/LogarithmicSpiral.html
 */
public class LogarithmicSpiral implements BallInBasketMovement{

    private float pointX, pointY;

    private float a,b;
    private float basketCenterX, basketCenterY;
    private float angle;

    public LogarithmicSpiral(float pointX, float pointY, float basketCenterX, float basketCenterY, float angle) {
        this.pointX = basketCenterX-pointX;
        this.pointY = basketCenterY-pointY;

        this.basketCenterX = basketCenterX;
        this.basketCenterY = basketCenterY;
        this.angle = angle;

        calculateParameters();

    }

    private void calculateParameters() {

        float distance=(float)Math.sqrt(pointX*pointX+pointY*pointY);



        a = (float)Math.pow(MathUtils.E,(angle*MathUtils.log(MathUtils.E,distance)+2*MathUtils.PI*MathUtils.log(MathUtils.E,distance)-angle)/(2*MathUtils.PI));

        b = (1-MathUtils.log(MathUtils.E,a))/(angle+2*MathUtils.PI);

        System.out.println("a: "+a+", b: "+b);
    }

    public Vector2 getCartesianPoint(float percent){
        float an = angle+percent*2*MathUtils.PI;
        float r = a * (float) Math.pow(MathUtils.E, b*(an));
        float x = r * MathUtils.cos(an)+basketCenterX;//-pointX
        float y = r * MathUtils.sin(an)+basketCenterY;//-pointY
        return new Vector2(x,y);
    }

}
