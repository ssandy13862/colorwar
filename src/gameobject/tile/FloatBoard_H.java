/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.tile;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import gameobject.obstacle.Obstacle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class FloatBoard_H extends Tile{
    private BufferedImage img;
    private int xEnd;
    private int xStart;
    private int xSpeed;
    private boolean floatButton;
    
    public FloatBoard_H(int x, int y, int width, int height) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.FLOAT_BOARD));
        xEnd = xStart = x;
        xSpeed = 1;
        floatButton = false;
    }
    
    public void move(){
        xEnd = xStart + 120;
        
        if(x == xEnd){
            floatButton = true;
        }else if(x == xStart){
            floatButton = false;
        }
        
        if(!floatButton){
            x += xSpeed;
        }else{
            x -= xSpeed;
        }
    }
    
    public int getxSpeed(){
        if(!floatButton){
            return xSpeed;
        }else{
            return -xSpeed;
        }
    }
    
    @Override
    public void paint(Graphics g) {
//        g.drawImage(img, x, y, null);
    }
    
    public void paint(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
//        g.drawRect(x, y, width, height);
    }

//    public boolean getFloatButton(){
//        return floatButton;
//    }
    
    @Override
    public int getColor() {
        return Global.COLOR_NONE;
    }
}
