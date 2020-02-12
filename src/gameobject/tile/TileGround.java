/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.tile;

import gameobject.tile.Tile;
import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author winniewang
 */

public class TileGround extends Tile{
    private BufferedImage img;
    private int tilePosition;

    public TileGround(int x, int y, int width, int height, int tilePosition) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.GROUND));
        this.tilePosition = tilePosition;
    }
    

    public void paint(Graphics g, int x, int y) {
        g.drawImage(img, x, y, x + width, y + height, 
                tilePosition * 40, 0, 
                40 + tilePosition * 40, 40, null);
    }
    
    @Override
    public int getColor(){
        return Global.COLOR_NONE;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }
}
