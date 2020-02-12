/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.AudioResourceController;
import controllers.ImageResourceController;
import controllers.PathBuilder;
import controllers.SceneControllers;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import static scene.Scene.startAudioBG;
import scene.StartScene.SkipAnima;
import scene.extra.ScoreSystem;
import scene.extra.TimerCounter;
import utils.CommandSolver;
import utils.CommandSolver.KeyCommandListener;
import utils.Global;
import values.AudioPath;
import values.ImagePath;

/**
 *
 * @author winniewang
 */
public class RankingScene extends Scene{

    private BufferedImage img;
    private PopupWindow popupWindow;
    private KeyCommandListener keyCommandListener;
    private boolean isReadOnly;
    private ArrayList<ScoreSystem> scoreSystems;
    private AudioResourceController arc;

    public RankingScene(SceneControllers sceneController, ScoreSystem scoreSystem, boolean isReadOnly) {
        super(sceneController);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_RANKING));
        
        popupWindow = new PopupWindow(scoreSystem);
        this.isReadOnly = isReadOnly;
        scoreSystems = PopupWindow.readScoreSystems();
        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                switch (commandCode) {
                    case Global.ACT_ENTER:
                        sceneController.changeScene(new StartScene(sceneController, new SkipAnima()));
                        break;
                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
            }
        };
    }

    @Override
    public void sceneBegin() {
        //播放音樂 Start
        arc = AudioResourceController.getInstance();
        startAudioBG = arc.getSound(PathBuilder.getAudio(AudioPath.START_BG_1));
        startAudioBG.loop(Clip.LOOP_CONTINUOUSLY);
        // End

    }

    @Override
    public void sceneUpdate() {
        popupWindow.update();
    }

    @Override
    public void sceneEnd() {
        startAudioBG.close();
    }

    @Override
    public KeyCommandListener getKeyCommandListener() {
//        if(!popupWindow.getIsFinished()){
//            return null;
//        }
        return keyCommandListener;
    }

    public CommandSolver.TypedListener getTypedListener(){
        return popupWindow.getTypedListener();
    }

    public ArrayList<ScoreSystem> sortScoreSystemsByScore(ArrayList<ScoreSystem> scoreSystems){
//        ArrayList<ScoreSystem> scoreSystems = popupWindow.getScoreSystems();
        //sortStart
        for(int i = 1; i < scoreSystems.size(); i++){
            for(int j = 0; j < scoreSystems.size() - i; j++){
                if(scoreSystems.get(j).getTotalScore() < scoreSystems.get(j+1).getTotalScore()){
                    ScoreSystem tmp = scoreSystems.get(j);
                    scoreSystems.set(j, scoreSystems.get(j+1));
                    scoreSystems.set(j+1, tmp);
                }
            }
        }
        return scoreSystems;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
        if(isReadOnly){
            scoreSystems = sortScoreSystemsByScore(scoreSystems);
            g.setFont(new Font("Quicksand", Font.BOLD, 25));
            for(int i = 0; i < scoreSystems.size(); i++){
                ScoreSystem sys = scoreSystems.get(i);
                TimerCounter cou = sys.getTimerCounter();
                g.drawString(sys.getName(), 360, 140 + i * 82);
                g.drawString(cou.toString(), 510, 140 + i * 82);
                g.drawString(sys.getTotalScore()+"", 690, 140 + i * 82);
                if(i == 4){
                    break;
                }
            }
            return;
        }
        if(!popupWindow.getIsFinished()){
            popupWindow.paint(g);

        }else{
            scoreSystems = sortScoreSystemsByScore(popupWindow.getScoreSystems());
            g.setFont(new Font("Quicksand", Font.BOLD, 25));
            for(int i = 0; i < scoreSystems.size(); i++){
                ScoreSystem sys = scoreSystems.get(i);
                TimerCounter cou = sys.getTimerCounter();
                g.drawString(sys.getName(), 360, 140 + i * 82);
//                g.drawString(sys.getBonus()+"/"+sys.getTotalBonus(), 450, 140 + i * 82);
                g.drawString(cou.toString(), 510, 140 + i * 82);
                g.drawString(sys.getTotalScore()+"", 690, 140 + i * 82);
                if(i == 4){
                    break;
                }
            }
        }
    }

}
