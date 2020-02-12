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
public class GuideScene extends Scene{
    
    private BufferedImage guideImg_3;
    private BufferedImage guideImg_4;
    private KeyCommandListener keyCommandListener;
    private int page;
    private AudioResourceController arc;

    public GuideScene(SceneControllers sceneController) {
        super(sceneController);
        ImageResourceController irc = ImageResourceController.getInstance();
        guideImg_3 = irc.tryGetImage(PathBuilder.getImg(ImagePath.GUIDE_3));
        guideImg_4 = irc.tryGetImage(PathBuilder.getImg(ImagePath.GUIDE_4));
        page = 0;
        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                switch (commandCode) {
                    case Global.ACT_ESC:
                        sceneController.changeScene(new StartScene(sceneController, new SkipAnima()));
                        break;
                    case Global.ACT_JUMP: //space鍵
                        page = 1;
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
    }

    @Override
    public void sceneEnd() {
        guideImg_3 = null;
        guideImg_4 = null;
        startAudioBG.close();
    }
    
    @Override
    public KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage((page == 0 ? guideImg_3 : guideImg_4), 0, 0, null);
    }
    
}
    
    
