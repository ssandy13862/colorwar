    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import java.awt.Graphics;
import utils.Global;

/**
 *
 * @author winniewang
 */
public abstract class GameObject {
    //物件的絕對座標
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    
    public GameObject(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    // coordinate start
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    // coordinate end
    
    //Bound Start
    public int getBottom(){
        return y + height;
    }
    
    public int getTop(){
        return y;
    }
    
    public int getLeft(){
        return x;
    }
    
    public int getRight(){
        return x + width;
    }
    
    public int getCenterX(){
        return (getLeft() + getRight()) / 2;
    }
    
    public int getCenterY(){
        return (getTop() + getBottom()) / 2;
    }
    
    //Bound end
    
    public int getColor(){
        return Global.COLOR_NONE;
    }
    
    public boolean isCollision(GameObject obj){
        if(getLeft() > obj.getRight()){
            return false;
        }
        if(getRight() < obj.getLeft()){
            return false;
        }
        if(getTop() > obj.getBottom()){
            return false;
        }
        if(getBottom() < obj.getTop()){
            return false;
        }
        return true;
            
    }
    
    public void paint(Graphics g, int x, int y){
        
    };
    
    public void paint(Graphics g){
        
    };
    
    
}
