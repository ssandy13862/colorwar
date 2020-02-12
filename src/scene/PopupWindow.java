/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;


import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.player.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import scene.extra.ScoreSystem;
import scene.extra.TimerCounter;
import utils.CommandSolver;
import utils.CommandSolver.KeyCommandListener;
import utils.CommandSolver.TypedListener;
import utils.DrawCenterText;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author winniewang
 */
public class PopupWindow {
    private TypedListener typedListener;
    private ArrayList<String> nameArray;
    private ArrayList<ScoreSystem> scoreSystems;
    private ScoreSystem scoreSystem;
    private DrawCenterText centerText;
    private String name;
    private boolean isFinished;
    private BufferedImage img;
    private KeyCommandListener keyCommandListener;
    
    public PopupWindow(String path){
        isFinished = false;
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(path);
        keyCommandListener = new CommandSolver.KeyCommandListener() {

            @Override
            public void keyPressed(int commandCode, long trigTime) {
                if(commandCode == Global.ACT_ESC){
                    isFinished = true;
                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                
            }
        };
    }


    public PopupWindow(ScoreSystem scoreSystem){
        nameArray = new ArrayList<>();
        scoreSystems = new ArrayList<>();
        this.scoreSystem = scoreSystem;
        name = "";
        isFinished = false;
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_POPUP));
        typedListener = new TypedListener(){

            @Override
            public void keyTyped(char c, long trigTime) {

                if(c == KeyEvent.VK_BACK_SPACE){
                    nameArray.remove(nameArray.size() - 1);
                }else if(c == KeyEvent.VK_SPACE){
                    isFinished = true;
                    saveFiles();
                }else{
                    if(65 <= (int)c && (int)c <= 90 || 97 <= (int)c && (int)c <= 122){
                        nameArray.add(String.valueOf(c));
                    }
                }
            }
        };
    }

    public boolean getIsFinished(){
        return isFinished;
    }

    public void update(){
        if(nameArray != null){
            name = "";
            for(int i = 0; i < nameArray.size(); i++){
                name += nameArray.get(i);
            }
        }
    }

    public void saveFiles(){
        scoreSystems = readScoreSystems();
        scoreSystem.setName(name);
        scoreSystems.add(scoreSystem);
        writeScoreSystems(scoreSystems);
//        System.out.println("pop"+scoreSystems);
    }
    
    //寫檔
    public void writeScoreSystems(ArrayList<ScoreSystem> scoreSystems){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("scoreSystem.txt"));
            for(int i = 0; i < scoreSystems.size(); i++){
                writer.write(scoreSystems.get(i).toString());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //讀檔
    public static ArrayList<ScoreSystem> readScoreSystems(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scoreSystem.txt"));
            ArrayList<ScoreSystem> tmpArray = new ArrayList<>();
            while(reader.ready()){
                String data[] = reader.readLine().split(",|:");
                ScoreSystem tmp = new ScoreSystem
                                (data[0],
                                 Integer.valueOf(data[1]),
                                 new TimerCounter(Integer.valueOf(data[2]),
                                                  Integer.valueOf(data[3]),
                                                  Integer.valueOf(data[4])));
                tmpArray.add(tmp);
//                System.out.println(tmp);
            }
            reader.close();
            return tmpArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<ScoreSystem> getScoreSystems(){
        return scoreSystems;
    }

//    public String getName(){
//        return name;
//    }

    public CommandSolver.TypedListener getTypedListener(){
        return typedListener;
    }
    
    public KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }

    public void paint(Graphics g){
        g.drawImage(img, 0, 0, null);
//        g.drawRect(230, 50, 600, 400);
//        g.setFont(new Font("Arial", Font.BOLD, 30));
//        g.drawString("Enter your name:", 410, 200);


//        g.drawRect(360, 250, 340, 80);
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setStroke(new BasicStroke(2.0f));
//        g.drawLine(370, 260, 370, 320);
        if(name != null){
            g.setColor(Color.WHITE);
            g.fillRect(450, 370, 160, 50);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString(name, 470, 405);
        }
        
//        DrawCenterText.paintCenteredText(450, 370, 160, 50, g, new Font("Arial", Font.BOLD, 60), nameForPrint);
//        if(name != null){
//
//        }
    }
    
}

            
