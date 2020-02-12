/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.prop;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class Rebirth extends GameObject{
    private BufferedImage img;
//    private boolean rebirthButtonRed;
    private boolean rebirthButton;
    private int color;

    public Rebirth(int x, int y, int width, int height, int color) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.REBIRTH));
        this.rebirthButton = false;
        this.color = color;
    }
    public int getColor(){
        return color;
    }
    
    public void setRebirth(){
        rebirthButton = true;
        
    }
    
    public boolean getRebirth(){
        return rebirthButton;
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }
    
    public void paint(Graphics g, int x, int y) {
        if(rebirthButton){
            g.drawImage(img, x, y, x + width, y + height, 
                             2 * 40, 0, 
                             40 + 2 * 40, 40, null);
        }else{
            g.drawImage(img, x, y, x + width, y + height, 
                         color * 40, 0, 
                         40 + color * 40, 40, null);
        }
    }
}
