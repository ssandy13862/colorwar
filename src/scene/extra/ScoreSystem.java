/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene.extra;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class ScoreSystem {
    
    private TimerCounter timerCounter;
    private BufferedImage img;
    private int bonus;
    private int totalScore;
    private String name;
    private int totalBonus;
    private int playerTotalBonus;
    
    public ScoreSystem(){
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_DIA));
        bonus = totalScore = playerTotalBonus = 0;
        this.timerCounter = new TimerCounter(900, 50);
//        totalScore = setTotalScore();
    }
    
    public ScoreSystem(String name, int totalScore, TimerCounter timerCounter){
        this.name = name;
        this.bonus = bonus;
        this.totalBonus = totalBonus;
        this.timerCounter = timerCounter;
        this.totalScore = totalScore;
        
    }
    
    public void bonusToZero(){
        bonus = 0;
    }
    public void totalbonusToZero(){
        totalBonus = 0;
    }
    
    public void addBonus() {
        bonus++;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public int getBonus(){
        return bonus;
    }
    
    public int getTotalBonus(){
        return totalBonus;
    }
    
    public void setTotalBonus(int totalBonus){
        this.totalBonus = totalBonus;
    }
    
    public int getTotalScore(){
        return totalScore;
    }
    
    public void setPlayerTotalBonus(int playerTotalBonus){
        this.playerTotalBonus = playerTotalBonus;
    }
    
    public TimerCounter getTimerCounter(){
        return timerCounter;
    }
    
    public int setTotalScore(){
        //30分鐘 - 使用時間 = 分數
//        System.out.println("timerCounter.getTotalSecond()"+timerCounter.getTotalSecond());
        int timer = timerCounter.getTotalSecond();
        totalScore = (timer > 1800 ? 0 : 1800 - timer)*10 + bonus * 50;
        return totalScore;
    }
    public void paint(Graphics g){
        //加鑽石圖案
        g.drawImage(img, 873, 66, null);
        g.drawString(bonus+"/"+totalBonus, 900, 80);
//        g.drawString(playerTotalBonus+"", 980, 80);
    }
    
    @Override
    public String toString(){
        return name +","+totalScore +","+timerCounter;
    }
    
}
