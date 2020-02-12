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
import gameobject.StartButton;
import gameobject.player.Player;
import gameobject.tile.Tile;
import gameobject.tile.TileGround;
import utils.CommandSolver.KeyCommandListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import scene.GameScene.OnePlayer;
import scene.GameScene.TwoPlayers;
import scene.extra.ScoreSystem;
import utils.Global;
import values.AudioPath;
import values.ImagePath;



/**
 *
 * @author user1
 */
public class StartScene extends Scene{

    public interface AnimaMode{
        public void animation(StartScene startScene);
    }

    public static class NormalAnima implements AnimaMode{

        @Override
        public void animation(StartScene startScene) {
            startScene.bg = startScene.irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_START));
            if(startScene.delayCounter.update()){
                startScene.contrail = ++startScene.contrail % 6;
            }

            if(startScene.moveUpAnima == -840){
                startScene.isBgArrived = true;
            }
            if(startScene.moveUpAnima > -840){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 1.0);
            }
            if(startScene.moveUpAnima > -800){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 1.1);
            }
            if(startScene.moveUpAnima > - 600){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 1.3);
            }
            if(startScene.moveUpAnima > - 350){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 1.2);
            }
            if(startScene.moveUpAnima > - 150){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 0.5);
            }
        }

    }

    public static class QuickAnima implements AnimaMode{

        @Override
        public void animation(StartScene startScene) {
            startScene.bg = startScene.irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_START));
            if(startScene.delayCounter.update()){
                startScene.contrail = ++startScene.contrail % 6;
            }

            if(startScene.moveUpAnima == -840){
                startScene.isBgArrived = true;
            }
            if(startScene.moveUpAnima > -840){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 2.0);
            }
            if(startScene.moveUpAnima > -800){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 2.2);
            }
            if(startScene.moveUpAnima > - 600){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 2.6);
            }
            if(startScene.moveUpAnima > - 350){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 2.4);
            }
            if(startScene.moveUpAnima > - 150){
                startScene.moveUpAnima =(int)(startScene.moveUpAnima - 1.0);
            }
        }

    }

    public static class SkipAnima implements AnimaMode{

        @Override
        public void animation(StartScene startScene) {
            startScene.bg = startScene.irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_SEC_START));}

    }

    private ImageResourceController irc;
    private BufferedImage bg;
    private BufferedImage text;
    private KeyCommandListener keyCommandListener;
    private DelayCounter delayCounter;
    private float moveUpAnima;
    private int contrail;
    private ArrayList<StartButton> startButtons;
    private ArrayList<Tile> tiles;
    private int buttonCount;
    private StartButton button;
    private Player player;
    private boolean isBgArrived;
    private AnimaMode animaMode;
    private AudioResourceController arc;
//    protected static Clip startAudioBG;


    public StartScene(SceneControllers sceneController, AnimaMode animaMode) {
        super(sceneController);
        buttonCount = -1;
        
        irc = ImageResourceController.getInstance();
        bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.BG_START));
        text = irc.tryGetImage(PathBuilder.getImg(ImagePath.TEXT));
        delayCounter = new DelayCounter(5);
        moveUpAnima = 0;
        contrail = 0;
        isBgArrived = false;
        this.animaMode = animaMode;


        keyCommandListener = new KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
//                System.out.println("commandCode"+commandCode);
                //人物移動
                switch (commandCode) {
                    case Global.ACT_RESTART:
                        startAudioBG.close();
//                        sceneController.changeScene(new StartScene(sceneController, new NormalAnima()));
                        sceneController.changeScene(new StartScene(sceneController, new SkipAnima()));
                        break;
                    case Global.ACT_PRESSED_RIGHT:
                        player.setDirection(new Player.MoveRightState());
                        break;
                    case Global.ACT_PRESSED_LEFT:
                        player.setDirection(new Player.MoveLeftState());
                        break;
                    case Global.ACT_JUMP:
                        player.jump(Global.Y_SPEED_DOUBLE);
                        break;
//                    case Global.ACT_ENTER:
//                        sceneController.changeScene(new StartScene(sceneController, new SkipAnima()));
//                        break;
                }
                //進入關卡
                if(commandCode == Global.ACT_ENTER){

                    switch (buttonCount){
                        case 0:
                            sceneController.changeScene(new GameScene(sceneController, new OnePlayer()));
                            break;
                        case 1:
                            sceneController.changeScene(new GameScene(sceneController, new TwoPlayers()));
                            break;
                        case 2:
                            sceneController.changeScene(new GuideScene(sceneController));
                            break;
                        case 3:
                            sceneController.changeScene(new RankingScene(sceneController, new ScoreSystem(), true));
                            break;
                    }
                }
            }
            @Override
            public void keyReleased(int commandCode, long trigTime) {
                switch (commandCode) {
                    case Global.ACT_PRESSED_RIGHT:
                        player.setDirection(new Player.StandState());
                        break;
                    case Global.ACT_PRESSED_LEFT:
                        player.setDirection(new Player.StandState());
                        break;
                }
            }
        };
    }

    public KeyCommandListener getKeyCommandListener(){
        return keyCommandListener;
    }

    @Override
    public void sceneBegin(){
        //播放音樂 Start
        arc = AudioResourceController.getInstance();
        startAudioBG = arc.getSound(PathBuilder.getAudio(AudioPath.START_BG_1));
        startAudioBG.loop(Clip.LOOP_CONTINUOUSLY);
        // End
        tiles = new ArrayList<>();
        startButtons = new ArrayList<>();
        player = new Player(400, 540, 40, 40, 3, 0);
        for(int i = 0; i < 4; i++){
            startButtons.add(new StartButton(175 + 190 * i, 338, 158, 70, i));
        }
        for(int i = 0; i < 27; i ++){
            tiles.add(new TileGround(i * 40, 540, 40, 40, 0));
        }

    }

    @Override
    public void sceneUpdate(){
        animaMode.animation(this);
        player.move();
        boolean isOnGround = false;
            for(int j = 0; j < tiles.size();j++){
                Tile tile = tiles.get(j);
                int col = player.checkCollisionDirection(tiles.get(j));
                if(col == Global.ACT_PRESSED_DOWN){
                    isOnGround = true;
                    player.setOnGround(tiles.get(j).getY());
                }
            }

            for(int i = 0; i < startButtons.size(); i++){
                StartButton startButton = startButtons.get(i);
                int col = player.checkCollisionDirection(startButton);
                if(col == Global.ACT_PRESSED_UP){
                    player.yStop(col);
                }else if(col == Global.ACT_PRESSED_LEFT || col == Global.ACT_PRESSED_RIGHT){
                    //左右碰撞
                    player.xStop(col, 0);
                }
            }

            //選擇關卡
            for(int i = 0; i < startButtons.size(); i++){
                button = startButtons.get(i);
//                    button.cleanHover();
                    if(button.isCollision(player)){
                        buttonCount = i;
//                        if(delayCounter.update()){
//                            sceneController.changeScene(new GameScene(sceneController));
//                        }
                        button.setHover();
                        break;
                    }

            switch(buttonCount){
                case 0:
                    startButtons.get(1).cleanHover();
                    startButtons.get(2).cleanHover();
                    startButtons.get(3).cleanHover();
                    break;
                case 1:
                    startButtons.get(0).cleanHover();
                    startButtons.get(2).cleanHover();
                    startButtons.get(3).cleanHover();
                    break;
                case 2:
                    startButtons.get(0).cleanHover();
                    startButtons.get(1).cleanHover();
                    startButtons.get(3).cleanHover();
                    break;
                case 3:
                    startButtons.get(0).cleanHover();
                    startButtons.get(1).cleanHover();
                    startButtons.get(2).cleanHover();
                    break;
            }
        }
    }

    @Override
    public void sceneEnd(){
        startAudioBG.close();
        bg = null;
    }

    @Override
    public void paint(Graphics g) {
        if(animaMode instanceof NormalAnima || animaMode instanceof QuickAnima){
            g.drawImage(bg, 0, (int)moveUpAnima, 1060, (int)moveUpAnima + 1480,
                    0 + contrail * 1060, 0,
                    1060 + contrail * 1060, 1480, null);
            if(isBgArrived){
                player.paintBigger(g);
                for(int i = 0; i < startButtons.size(); i++){
                    startButtons.get(i).paint(g);
                }
                g.drawImage(text, 291, 253, null);
            }
        }else{
            g.drawImage(bg, 0, 0, null);
            player.paintBigger(g);
            for(int i = 0; i < startButtons.size(); i++){
                startButtons.get(i).paint(g);
            }
        }

    }

}
