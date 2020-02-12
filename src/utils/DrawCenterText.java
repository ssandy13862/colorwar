/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author user
 */
public class DrawCenterText {
    private int x0;
    private int y0;
    private int x;
    private int y;
    private int width;
    private int height;
    private Graphics g;
    private Font font;
    private String text;
    private static FontMetrics metrics;
    
    public DrawCenterText(int x0, int y0, Graphics g, Font font, String text, int width, int height){
        this.x0 = x0;
        this.y0 = y0;
        this.g = g;
        this.font = font;
        this.text = text;
        this.width = width;
        this.height = height;
        metrics = g.getFontMetrics(font);
        this.x = x0 + (width - metrics.stringWidth(text)) / 2;
        this.y = y0 + (height - metrics.getHeight()) / 2 + metrics.getAscent();
    }
    
    public static void paintCenteredText(int recX0, int recY0, int recWidth, int recHeight, Graphics g, Font font, String text){
        metrics = g.getFontMetrics(font);
        int x = recX0 + (recWidth - metrics.stringWidth(text)) / 2;
        int y = recY0 - 10 + (recHeight - metrics.getHeight()) / 2 + metrics.getAscent();
        System.out.println("座標" + x + y);
        g.drawString(text, x, y);
    }
    
    public void update(int width, int height){
        this.x = x0 + (width - metrics.stringWidth(text)) / 2;
        this.y = y0 + (height - metrics.getHeight()) / 2 + metrics.getAscent();
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getText(){
        return text;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    //setter
    public void setFont(Font font){
        this.font = font;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setGraphics(Graphics g){
        this.g = g;
    }
    public void setStartPoint(int x0, int y0){
        this.x0 = x0;
        this.y0 = y0;
    }
}
