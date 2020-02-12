/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.prop;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import gameobject.player.Player;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author winniewang
 */
public class Flag extends GameObject{
    private BufferedImage img;

    public Flag(int x, int y, int width, int height) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.FLAG));
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }
    
    public void paint(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
    }
    
    
 
}
