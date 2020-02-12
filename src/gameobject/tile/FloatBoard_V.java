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
public class FloatBoard_V extends Tile{
    private BufferedImage img;
    private int yEnd;
    private int yStart;
    private boolean floatButton;
    
    public FloatBoard_V(int x, int y, int width, int height) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.FLOAT_BOARD));
        yEnd = yStart = y;
        floatButton = false;
    }
    
    public void move(){
        yEnd = yStart + 120;
        
        if(y == yEnd){
            floatButton = true;
        }else if(y == yStart){
            floatButton = false;
        }
        
        if(!floatButton){
            y++;
        }else{
            y--;
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

    public boolean getFloatButton(){
        return floatButton;
    }
    
    @Override
    public int getColor() {
        return Global.COLOR_NONE;
    }
}
