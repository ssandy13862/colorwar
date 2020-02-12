/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.player.Player;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class Camera extends MovableGameObject{

    private Player player;
    private int imgWidth;
    private BufferedImage bg;

    private float x;

    
    public Camera(int x, int y, int width, int height, int speedCount, Player player, int imgWidth) {
        super(x, y, width, height, speedCount);
        ImageResourceController irc = ImageResourceController.getInstance();
        this.bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_1));
        this.player = player;
        this.imgWidth = imgWidth;
    }
    
    @Override
    public void move() {
       int target = player.x - 530;
       if(player.x < 530){
           target = 0;
       }else if(player.x > imgWidth - 530){
           target = imgWidth - 1060;
       }
//       System.out.println(target + " " + player.x);
       float v = target - this.x;
//       System.out.println(v);
       x += v / 30f;
//        if(player.x <= 530){
//            x = 0;
//        }else if(player.x > 530 && player.x <= imgWidth - 530){
//            x = player.x - 530;
//            
//        }else if(player.x > imgWidth - 530){
//            x = imgWidth - 1060;
//        }
    }
    

    public void paint(Graphics graphics, GameObject objs) {
        if(objs.getX() < x - 41 || objs.getX() > x + 1060){
            return;
        }
        objs.paint(graphics, objs.getX() - Math.round(x), objs.getY());
//        graphics.drawLine(530, y, 530, height);
//        System.out.println("玩家座標:"+player.x + " " + player.y);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(bg, 0, 0, null);
        
    }
   
}
