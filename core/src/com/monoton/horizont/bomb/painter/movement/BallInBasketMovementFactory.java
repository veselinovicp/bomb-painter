package com.monoton.horizont.bomb.painter.movement;

import com.monoton.horizont.bomb.painter.Constants;

public class BallInBasketMovementFactory {

    public static BallInBasketMovement getMovement(String type, float pointX, float pointY, float basketCenterX, float basketCenterY, float angle){
        if(type.equals(Constants.IN_BASKET_MOVEMENT_LOGARITHMIC_SPIRAL)){
            return new LogarithmicSpiral(pointX,pointY,basketCenterX, basketCenterY,angle);
        }
        if(type.equals(Constants.IN_BASKET_MOVEMENT_GRADUAL)){
            return new GradualFalling(pointX,pointY,basketCenterX, basketCenterY,angle);
        }
        throw new RuntimeException("No BallInBasketMovement by type: "+type);

    }
}
