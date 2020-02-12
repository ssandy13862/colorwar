/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.tile;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author winniewang
 */
public class SmallJumpBoard extends JumpBoard{
    private BufferedImage img;

     public SmallJumpBoard(int x, int y, int width, int height) {
        super(x, y, width, height);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.SMALL_JUMP_BOARD));
    }

    @Override
    public int getColor() {
        return Global.COLOR_NONE;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    @Override
    public void paint(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
//        g.drawRect(x, y, width, height);
    }

    
    
}
