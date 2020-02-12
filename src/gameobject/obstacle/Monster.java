/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.obstacle;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class Monster extends Obstacle{
    private BufferedImage img;
    private int xEnd;
    private int xStart;
    private boolean monsButton;
    private DelayCounter delayCounter;
    private int act;
    private int[] actPosition = {0, 1, 2, 1};

    public Monster(int x, int y, int width, int height) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.MONSTER));
        xEnd = xStart = x;
        act = 0;
        monsButton = false;
        delayCounter = new DelayCounter(3);
    }
    
    public void move(){
        xEnd = xStart + 200;
        
        if(!monsButton){
            x++;
        }else{
            x--;
        }
        
        if(x == xEnd){
            monsButton = true;
        }else if(x == xStart){
            monsButton = false;
        }
        
        if(delayCounter.update()){
            act = ++act % 4;
        }
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }
    
    public void paint(Graphics g, int x, int y) {
        g.drawImage(img, x, y, x + width, y + height, 
                actPosition[act] * 40, 0, 
                40 + actPosition[act] * 40, 40, null);
//        g.drawRect(x, y, width, height);
    }
}
