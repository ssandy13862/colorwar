/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.obstacle;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.obstacle.Obstacle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author winniewang
 */
public class RedWaveTwo extends Wave{
    private BufferedImage img;

    public RedWaveTwo(int x, int y, int width, int height) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.RED_WAVE_TWO));
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }
    
    @Override
    public void paint(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
    }
    
    @Override
    public int getColor() {
        return Global.COLOR_RED_0;
    }
    
}
