///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package gameobject;
//
//import controllers.ImageResourceController;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
//import utils.Global;
//
///**
// *
// * @author user1
// */
//public class Button extends GameObject {
//    
//    private BufferedImage img;
//    int button_number;
//
//    public interface ButtonListener {
//        public void onClick(int x, int y);
//        public void hover(int x, int y);
//    }
//
//    private ButtonListener buttonListener;
//
//    public Button(int x, int y, int width, int height, int button_number) {
//        super(x, y, width, height);
//        ImageResourceController irc = ImageResourceController.getInstance();
//        img = irc.tryGetImage("/resources/imgs/background/button1.png");
//        this.button_number = button_number;
//    }
//
//    public void setButtonListener(ButtonListener buttonListener) {
//        this.buttonListener = buttonListener;
//    }
//
//    public boolean isCollision(int x, int y) {
//        if (x < this.x || x > this.x + this.width) {
//            return false;
//        }
//        if (y < this.y || y > this.y + this.height) {
//            return false;
//        }
//        return true;
//    }
//
//    public void click(int x, int y) {
//        if (buttonListener == null) {
//            return;
//        }
//        buttonListener.onClick(x, y);
//    }
//
//    public void hover(int x, int y) {
//        if (buttonListener == null) {
//            return;
//        }
//        buttonListener.hover(x, y);
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        
//        int countX = 270 * button_number;
//        
//        g.drawImage(img, x, y, x + width, y + height, 
//                countX , 0,  
//                countX + 270, 70 ,
//                null);
//        g.setColor(Color.BLUE);
////        g.drawRect(x, y, width, height);
////        g.fillRect(x, y, width, height);
//    }
//
//}
