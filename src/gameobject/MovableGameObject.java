/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import utils.Global;

/**
 *
 * @author ghost
 */
public abstract class MovableGameObject extends GameObject{
    
    protected int speedCount;
    private int jumpSpeed;

    
    public MovableGameObject(int x, int y, int width, int height, int speedCount){
        super(x, y, width, height);
        setSpeedCount(speedCount);
        setJumpSpeed(speedCount);
    }
      
    public final void setSpeedCount(int speedCount){
        this.speedCount = speedCount * Global.ACT_SPEED;
    }
        
    public int getSpeedCount(){
        return speedCount;
    }
    
    public void setJumpSpeed(int speedCount){
        this.jumpSpeed = speedCount * 2 * Global.ACT_SPEED;
    }
    
    
    public int getJumpSpeed(){
        return jumpSpeed;
    }

    public abstract void move();
 
}
