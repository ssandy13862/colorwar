/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author ghost
 */
 public class StartButton extends GameObject{
    
    BufferedImage img;
    private int position;
    private int hover;

    public StartButton(int x, int y, int width, int height, int position) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.BUTTON));
        this.position = position * 158;
        hover = 0;
    }
    public void setHover(){
        hover = 1;
    }
    
    public void cleanHover(){
        hover = 0;
    }
    
    @Override
    public void paint(Graphics g) {
        
        g.drawImage(img, x, y, x + width, y + height,
                position, 0 + 70 * hover,  
                position + 158 , 70 + 70 * hover,
                null);
    }
    
}
