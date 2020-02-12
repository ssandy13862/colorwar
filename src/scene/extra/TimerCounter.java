/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene.extra;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.FontManager;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class TimerCounter{

    private BufferedImage img;
    private long startTime;
    private long totalMilliSecond;
    private long currentTime;
    private int totalSecond;
    private int second;
    private int minute;
    private int hour;
    private Font font;
    //
    private int x;
    private int y;
    


    public TimerCounter(int x, int y) {
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_TIME));
        this.x = x;
        this.y = y;
        font = FontManager.getInstance().getFont(FontManager.TIMER);
    }

    public TimerCounter(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    
    public int getHour(){
        return hour;
    }
    
    public int getMinute(){
        return minute;
    }
    
    public int getSecond(){
        return second;
    }
    
    public void timeStart() {
        startTime = System.currentTimeMillis();
//        System.out.println("startTime"+startTime);
    }

    public void timeUpdate() {
        
        currentTime = System.currentTimeMillis() - startTime;
//        System.out.println("currentTime"+currentTime);
        covertTime();
        
    }

    private void covertTime() {
        totalSecond = (int) currentTime / 1000;
        second = totalSecond % 60 ;
        minute = totalSecond / 60;
        hour = totalSecond / 60 / 60;
    }

    public int getTotalSecond(){
//        System.out.println("totalSecond"+totalSecond);
        return totalSecond;
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.setFont(font);
        //加時間圖案
        g.drawImage(img, x - 28, y - 16, null);
        g.drawString("" + (hour >= 10 ? hour : "0" + hour), x, y);
        g.drawString(":", x + 27, y);
        g.drawString("" + (minute >= 10 ? minute : "0" + minute), x + 35, y);
        g.drawString(":", x + 61, y);
        g.drawString("" + (second >= 10 ? second : "0" + second), x + 70, y);
    }
    
    public String toString(){
        
        return (hour >= 10 ? hour : "0" + hour) + ":" + 
               (minute >= 10 ? minute : "0" + minute) + ":" + 
               (second >= 10 ? second : "0" + second);
    }
}
