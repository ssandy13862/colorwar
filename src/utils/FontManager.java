/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

/**
 *
 * @author ghost
 */
public class FontManager {
    public static final String TIMER = "TIMER";
    
    private HashMap<String, Font> fonts;
    
    private static FontManager fontManager;
    
    private FontManager(){
        fonts = new HashMap<>();
    }
    
    public static FontManager getInstance(){
        if(fontManager == null){
            fontManager = new FontManager();
        }
        return fontManager;
    }
    
    public void preLoading(Graphics g){
        load(TIMER, new Font("Quicksand", Font.BOLD, 20), g);
    }
    
    public Font getFont(String str){
        return fonts.get(str);
    }
    
    private void load(String str, Font font, Graphics g){
        fonts.put(str, font);
        g.setFont(font);
        g.drawString("", -500, -500);
    }
}
