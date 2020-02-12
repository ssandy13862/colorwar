/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class Cloud extends GameObject{
    
    private BufferedImage cloud;
    private DelayCounter delayCounter;
    private int x2;
    
    
    public Cloud(int x, int y, int width, int height){
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        cloud = irc.tryGetImage(PathBuilder.getImg(ImagePath.CLOUD));
        delayCounter = new DelayCounter(1);
        x2 = cloud.getWidth();
        
    }
    
    public BufferedImage getImg(){
        return cloud;
    }
    public int getX2(){
        return x2;
    }
    public void move(){
        if(delayCounter.update()){
            --x;
            --x2;
            if(x == -cloud.getWidth()){
                x = cloud.getWidth();
            }
            if(x2 == -cloud.getWidth()){
                x2 = cloud.getWidth();
            }
        }
    }
    public void paint(Graphics graphics){
        graphics.drawImage(cloud, x, 0, null);
        graphics.drawImage(cloud, x2, 0, null);
    }
}
