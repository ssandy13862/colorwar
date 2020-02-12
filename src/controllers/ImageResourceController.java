/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author winniewang
 */
public class ImageResourceController {
    
    private class KeyPair{
        private BufferedImage img;
        private String path;
        
        public KeyPair(BufferedImage img, String path){
            this.img = img;
            this.path = path;
        }
    }
    private static ImageResourceController irc;
    private ArrayList<KeyPair> imgPairs;
    
    
    private ImageResourceController(){
        imgPairs = new ArrayList<>();
        
    }
    
    public static ImageResourceController getInstance(){
        if(irc == null){
            irc = new ImageResourceController();
        }
        return irc;
    }
    
    public BufferedImage tryGetImage(String path){
        KeyPair pair = findKeyPair(path);
        if(pair == null){
            return addImage(path);
        }
        return pair.img;
    }
    
    private KeyPair findKeyPair(String path){
        for(int i = 0; i < imgPairs.size(); i++){
            KeyPair pair = imgPairs.get(i);
            if(pair.path.equals(path)){
                return pair;
            }
        }
        return null;
    }
    
    private BufferedImage addImage(String path){
        try{
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            imgPairs.add(new KeyPair(img, path));
            return img;
        }catch(IOException e){
            
        }
        return null;
    }
}
