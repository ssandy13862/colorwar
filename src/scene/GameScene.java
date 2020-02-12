/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.AudioResourceController;
import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.obstacle.Wave;
import gameobject.tile.*;
import controllers.SceneControllers;
import gameobject.player.Player;
import gameobject.*;
import gameobject.level.Level;
import gameobject.player.Player.*;
import gameobject.prop.Flag;
import gameobject.obstacle.Obstacle;
import gameobject.prop.Diamond;
import gameobject.prop.Rebirth;
import gameobject.prop.RebirthPair;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import scene.StartScene.SkipAnima;
import scene.extra.ScoreSystem;
import utils.Global;
import utils.CommandSolver.KeyCommandListener;
import values.AudioPath;
import values.ImagePath;

/**
 *
 * @author user1
 */

public class GameScene extends Scene {

    public interface PlayerMode{
        public int action(GameScene gameScene, SceneControllers sceneController);
    }

    public static class OnePlayer implements PlayerMode{

        @Override
        public int action(GameScene gameScene, SceneControllers sceneController) {
//            gameScene.levelnext = new Level(getLevelNext());
//            gameScene.levelnext = new Level(ImagePath.LEVEL1_3);
            gameScene.keyCommandListener = new KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {

                switch (commandCode) {
                    //回主選單
                    case Global.ACT_RESTART:
                        audioBG.close();
                        sceneController.changeScene(new StartScene(sceneController, new SkipAnima()));
                        break;
                    case Global.ACT_PRESSED_RIGHT:
                        for (int i = 0; i < gameScene.players.size(); i++) {
                            gameScene.players.get(i).setDirection(new MoveRightState());
                        }
                        break;
                    case Global.ACT_PRESSED_LEFT:
                        for (int i = 0; i < gameScene.players.size(); i++) {
                            gameScene.players.get(i).setDirection(new MoveLeftState());
                        }
                        break;
                    case Global.ACT_JUMP:
                        for (int i = 0; i < gameScene.players.size(); i++) {
                            gameScene.players.get(i).jump(Global.Y_SPEED);
                        }
                        break;
                    case Global.ACT_CHANGE_PLAYER:
                        int tmpColor = gameScene.players.get(0).getColor();
                        gameScene.players.get(0).setColor(gameScene.players.get(1).getColor());
                        gameScene.players.get(1).setColor(tmpColor);
                        break;
                    case Global.ACT_ESC:
                        guideImg_1 = null;
//                        System.out.println("esc");
                        break;
                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                switch (commandCode) {
                    case Global.ACT_PRESSED_RIGHT:
                        for (int i = 0; i < gameScene.players.size(); i++) {
                            gameScene.players.get(i).setDirection(new StandState());
                        }
                        break;
                    case Global.ACT_PRESSED_LEFT:
                        for (int i = 0; i < gameScene.players.size(); i++) {
                            gameScene.players.get(i).setDirection(new StandState());
                        }
                        break;
                }
            }
            };
            return 1;
        }

    }

    public static class TwoPlayers implements PlayerMode{


        @Override
        public int action(GameScene gameScene, SceneControllers sceneController) {
//            gameScene.levelnext = new Level(get2LevelNext());
//            gameScene.levelnext = new Level(ImagePath.LEVEL2_1);
            gameScene.keyCommandListener = new KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                switch (commandCode) {

                    case Global.ACT_RESTART:
                        audioBG.close();
                        sceneController.changeScene(new StartScene(sceneController, new SkipAnima()));
                        break;
                    case Global.ACT_2_RIGHT:
                        gameScene.players.get(0).setDirection(new MoveRightState());
                        gameScene.players.get(1).setDirection(new MoveRightState());
                        break;
                    case Global.ACT_2_LEFT:
                        gameScene.players.get(0).setDirection(new MoveLeftState());
                        gameScene.players.get(1).setDirection(new MoveLeftState());
                        break;
                    case Global.ACT_2_JUMP:
                        gameScene.players.get(0).jump(Global.Y_SPEED);
                        gameScene.players.get(1).jump(Global.Y_SPEED);
                        break;
                    case Global.ACT_PRESSED_RIGHT:
                        gameScene.players.get(2).setDirection(new MoveRightState());
                        gameScene.players.get(3).setDirection(new MoveRightState());
                        break;
                    case Global.ACT_PRESSED_LEFT:
                        gameScene.players.get(2).setDirection(new MoveLeftState());
                        gameScene.players.get(3).setDirection(new MoveLeftState());
                        break;
                    case Global.ACT_JUMP:
                        gameScene.players.get(2).jump(Global.Y_SPEED);
                        gameScene.players.get(3).jump(Global.Y_SPEED);
                        break;
                    case Global.ACT_ESC:
                        guideImg_2 = null;
                        break;

                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                switch (commandCode) {
                    case Global.ACT_2_RIGHT:
                        gameScene.players.get(0).setDirection(new StandState());
                        gameScene.players.get(1).setDirection(new StandState());
                        break;
                    case Global.ACT_2_LEFT:
                        gameScene.players.get(0).setDirection(new StandState());
                        gameScene.players.get(1).setDirection(new StandState());
                        break;
                    case Global.ACT_PRESSED_RIGHT:
                        gameScene.players.get(2).setDirection(new StandState());
                        gameScene.players.get(3).setDirection(new StandState());
                        break;
                    case Global.ACT_PRESSED_LEFT:
                        gameScene.players.get(2).setDirection(new StandState());
                        gameScene.players.get(3).setDirection(new StandState());
                        break;

                }
            }
            };
            return 2;
        }
    }

    private ArrayList<Player> players;
    private Camera camera;
    private KeyCommandListener keyCommandListener;
    private ArrayList<Tile> tiles;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<RebirthPair> rebirthPairs;
    private ArrayList<Diamond> diamonds;
    private Flag flag;
    private Level levelnext;
    private static int levelCount;
    private Cloud cloud;
    private static Clip audioBG;
    private int playerTotalBonus;
    private ScoreSystem scoreSystem;
    private AudioResourceController arc;
    private PlayerMode playerMode;
    private int modeNumber;
    private static BufferedImage guideImg_1;
    private static BufferedImage guideImg_2;
    private ImageResourceController irc;
    private PopupWindow popupWindow;

    public GameScene(SceneControllers sceneController, PlayerMode playerMode) {
        super(sceneController);
        levelCount = 0;
        scoreSystem = new ScoreSystem();
        //播放音樂 Start
        arc = AudioResourceController.getInstance();
        this.audioBG = arc.getSound(PathBuilder.getAudio(AudioPath.GAME_BG_1));
        audioBG.loop(Clip.LOOP_CONTINUOUSLY);
        // End
        irc = ImageResourceController.getInstance();
        this.playerMode = playerMode;
        this.modeNumber = playerMode.action(this, sceneController);

    }

    @Override
    public void sceneBegin() {

        //分數計算 Start
        scoreSystem.bonusToZero();
        scoreSystem.totalbonusToZero();
        if(levelCount == 0){
            scoreSystem.getTimerCounter().timeStart();
            playerTotalBonus = 0;
        }


        //載入關卡 Start
        switch(modeNumber){
            case 1:
//                levelnext = new Level(ImagePath.LEVEL1_3);
                levelnext = new Level(getLevelNext());
                if(levelCount == 0){
//                    guideImg_1 = irc.tryGetImage(PathBuilder.getImg(ImagePath.GUIDE_1));
                    popupWindow = new PopupWindow(PathBuilder.getImg(ImagePath.GUIDE_1));
                }

                break;
            case 2:
//                levelnext = new Level(ImagePath.LEVEL2_2);
//                levelnext = new Level(ImagePath.LEVEL_TEST);
                levelnext = new Level(get2LevelNext());
                //ImagePath.LEVEL2_1
                if(levelCount == 0){
//                    guideImg_2 = irc.tryGetImage(PathBuilder.getImg(ImagePath.GUIDE_2));
                    popupWindow = new PopupWindow(PathBuilder.getImg(ImagePath.GUIDE_2));
                }
                break;
        }
        cloud = new Cloud(0, 0, 2000, 640);
        players = levelnext.getPlayers();
//        System.out.println(players);
        camera = new Camera(0, 0, 600, 640, 3, players.get(0), levelnext.getWidth());
        obstacles = levelnext.getObstacles();
        rebirthPairs = levelnext.getRebirthPairs();
        diamonds = levelnext.getDiamonds();
        scoreSystem.setTotalBonus(diamonds.size());
        tiles = levelnext.getTiles();
        flag = levelnext.getflag();
    }

    public static String getLevelNext() {
        switch (levelCount) {
            case 0:
                return ImagePath.LEVEL1_1;
            case 1:
                return ImagePath.LEVEL1_2;
            case 2:
                return ImagePath.LEVEL1_3;
        }
        return null;
    }

    public static String get2LevelNext() {
        switch (levelCount) {
            case 0:
                return ImagePath.LEVEL2_1;
            case 1:
                return ImagePath.LEVEL2_2;
            case 2:
                return ImagePath.LEVEL2_3;
        }
        return null;
    }

    @Override
    public void sceneUpdate() {

        //開始計時
//        System.out.println("scoreSystem.getTimerCounter()"+scoreSystem.getTimerCounter());
        scoreSystem.getTimerCounter().timeUpdate();


        //gameobject移動 Start
        camera.move();
        cloud.move();
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).move();
        }

        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).move();
        }

        boolean dead = false;
        for (int i = 0; i < players.size(); i++) {
            players.get(i).move();
            if (players.get(i).isDeadState()) {
                dead = true;
            }
        }
        if (dead) {
            return;
        }
        //gameobject移動 end

        //把所有角色的狀態設回Default
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setStackState(new DefaultState());
        }

        //角色之間的碰撞 Start
        for (int i = 0; i < players.size(); i++) {
            boolean isCollide = false;
            for (int j = 0; j < players.size(); j++) {
                if (j == i) {
                    continue;
                }

                int colPlayer = players.get(i).checkPlayerCollision(players.get(j));
                if(colPlayer == -1){
                    continue;
                }

                //i 的狀態是Default
                if (players.get(i).getStackState() instanceof DefaultState){
                    playerCheckDefault(colPlayer, players.get(i), players.get(j));

                //if i 狀態是Bottom
                }else if (players.get(i).getStackState() instanceof BottomState) {
                    playerCheckBottom(colPlayer, players.get(i), players.get(j));

                //if i 狀態是Head
                }else if(players.get(i).getStackState() instanceof HeadState){
                    playerCheckHead(colPlayer, players.get(i), players.get(j));

                //if i 狀態是Center
                }else if (players.get(i).getStackState() instanceof CenterState){
                    playerCheckCenter(colPlayer, players.get(i), players.get(j));

                }
//                System.out.println("player :" + i + " color " + players.get(i).getColor() + " " + players.get(i).getStackState().getClass().getSimpleName());
//                System.out.println("player :" + j + " color " + players.get(j).getColor() + " " + players.get(j).getStackState().getClass().getSimpleName());
//                System.out.println("----------------------");
            }
        }
        //角色之間的碰撞 End

//        for (int i = 0; i < players.size(); i++) {
//
//            System.out.println("player :" + i + " color " + players.get(i).getColor()
//                    + " " + players.get(i).getStackState().getClass().getSimpleName());
////                    for(int j = 0; j < players.size(); j++){
////                        if(players.get(i).getBottomPlayer() == null){
////                            System.out.println("no bottomPlayer");
////                        }else if (players.get(i).getBottomPlayer().equals(players.get(j))){
////                            System.out.println("第" + j + "個player");
////                        }
////                    }
//        }


        //角色 & 物品的碰撞 Start
        for (int i = 0; i < players.size(); i++) {

            Player player = players.get(i);
            //超出邊界
            if (player.getY() > 640) {
                playersAllDead();
                return;// if dead, no need to check more
            }

            //判斷尖刺、怪物、海苔條
            for (int n = 0; n < obstacles.size(); n++) {
                Obstacle obstacle = obstacles.get(n);

                if (player.isCollision(obstacle)) {
                    if (obstacle instanceof Wave && !player.isOnWrongColor(obstacle)) {
                        continue;
                    }
                    playersAllDead();
                    return;
                }
            }

            //bonus碰撞加分
            for (int j = 0; j < diamonds.size(); j++) {
                if (players.get(i).isCollision(diamonds.get(j))) {
                    arc.playSound(PathBuilder.getAudio(AudioPath.COIN));
                    scoreSystem.addBonus();
                    diamonds.remove(j);
                }
            }

            //重生點判斷
            rebirth(rebirthPairs, player);


            //碰旗子過關
            if (flag.isCollision(players.get(i))) {
                arc.playSound(PathBuilder.getAudio(AudioPath.FLAG));
//                System.out.println("過關!");
                scoreSystem.setTotalScore();
                playerTotalBonus += scoreSystem.getBonus();
                scoreSystem.setPlayerTotalBonus(playerTotalBonus);
                if(levelCount == 2){
                    sceneController.changeScene(new RankingScene(sceneController, scoreSystem, false));
                    //musicStop
                    audioBG.close();
                }else{
                    sceneEnd();
                }
            }

            //和地板的碰撞判斷 Start
            boolean isOnGround = false;
            boolean isDead = false;
            for (int j = 0; j < tiles.size(); j++) {
                Tile tile = tiles.get(j);
                int col = player.checkCollisionDirection(tile);
                if (col == -1) {
                    continue;
                }

                if (player.getStackState() instanceof BottomState) {
                    // isOnGround ,isDead
                    if(checkBottom(player, tile, col)[1]){
                        return;
                    }
                    isOnGround = checkBottom(player, tile, col)[0] || isOnGround;
                }
                if (player.getStackState() instanceof HeadState) {
                    checkHead(player, tile, col);
                }
                if (player.getStackState() instanceof DefaultState) {
                    // collision
                    if(checkDefault(player, tile, col)[1]){
                        return;
                    }
                    isOnGround = checkDefault(player, tile, col)[0] || isOnGround;
                }
                if (player.getStackState() instanceof CenterState) {
                    // collision
                    checkCenter(player, tile, col);
                }
            }
            if (!isOnGround && player.getStackState() instanceof BottomState ) {
                player.fall();
            }
            if (!isOnGround && player.getStackState() instanceof DefaultState) {
                player.fall();
            }
            //和地板的碰撞判斷 End
        }
        //角色&物品的碰撞 End
    }

    private void rebirth(ArrayList<RebirthPair> rebirthPairs, Player player){
        for(int i = 0; i < rebirthPairs.size(); i++){
            RebirthPair rebirthPair = rebirthPairs.get(i);

            Rebirth rebirth1 = rebirthPair.getRebirth1();
            Rebirth rebirth2 = rebirthPair.getRebirth2();

            if(rebirth1.isCollision(player)){
                if(rebirth1.getColor() == 0 && player.getColor() == 0){
                    rebirth1.setRebirth();
                }

            }
            if(rebirth2.isCollision(player)){
                if(rebirth2.getColor() == 1 && player.getColor() == 1){
                    rebirth2.setRebirth();
                }
            }
            if(rebirthPair.getIsCollide()){
                if(rebirth1.getColor() == 0 && player.getRebirthColor() == 0){
                    player.setRebirthX(rebirth1.getX());
                    player.setRebirthY(rebirth1.getY());

                }else if(rebirth2.getColor() == 1 && player.getRebirthColor() == 1){
                    player.setRebirthX(rebirth2.getX());
                    player.setRebirthY(rebirth2.getY());
                }
            }
        }

    }

    private void playerCheckCenter(int colPlayer, Player playerI, Player playerJ){
        if (colPlayer == Global.ACT_PRESSED_LEFT || colPlayer == Global.ACT_PRESSED_RIGHT) {
            int offset = 0;
            if (colPlayer == Global.ACT_PRESSED_LEFT) {
                offset = playerJ.getRight() - playerI.getLeft();
            } else {
                offset = playerJ.getLeft() - playerI.getRight();
            }
            playerI.xStop(colPlayer, offset);
        }
    }

    private void playerCheckHead(int colPlayer, Player playerI, Player playerJ){
        //第j隻在第i隻的頭上
        if(colPlayer == Global.ACT_PRESSED_DOWN){
//            System.out.println("Head向下碰撞return");
            return;
        }
        if(colPlayer == Global.ACT_PRESSED_UP){
            if(playerJ.getStackState() instanceof BottomState){
                playerJ.setOnGround(playerI.getY());
                playerJ.setStackState(new CenterState());
                playerJ.setBottomPlayer(playerI);

                playerI.setStackState(new CenterState());

            }else if(playerJ.getStackState() instanceof DefaultState){
//                System.out.println("Head 撞到 Default");
                playerJ.setOnGround(playerI.getY());
                playerJ.setStackState(new HeadState());
                playerJ.setBottomPlayer(playerI);

                //把自己原本的bottomPlayer再傳到CenterState的bottomPlayer
                playerI.setStackState(new CenterState());
            }

        } else if (colPlayer == Global.ACT_PRESSED_LEFT || colPlayer == Global.ACT_PRESSED_RIGHT) {
            int offset = 0;
            if (colPlayer == Global.ACT_PRESSED_LEFT) {
                offset = playerJ.getRight() - playerI.getLeft();
            } else {
                offset = playerJ.getLeft() - playerI.getRight();
            }
            playerI.xStop(colPlayer, offset);
        }
    }

    private void playerCheckBottom(int colPlayer, Player playerI, Player playerJ){
        if(colPlayer == Global.ACT_PRESSED_UP){
//            System.out.println("Bottom向上碰撞return");
            return;
        }
        //第i隻在第j隻的頭上
        //下方碰撞
        if (colPlayer == Global.ACT_PRESSED_DOWN) {
//                        System.out.println("第" + i + "隻下方碰撞");
            if(playerJ.getStackState() instanceof HeadState){
//                System.out.println("Bottom 撞到 Head");
                playerI.setStackState(new CenterState());
                playerI.setOnGround(playerJ.getY());
                playerI.setBottomPlayer(playerJ);

                playerJ.setStackState(new CenterState());
//                System.out.println("playerJ.bottomPlayer" + playerJ.getBottomPlayer());
            }else if(playerJ.getStackState() instanceof DefaultState){
                //把第i隻的y設為j+height
                playerI.setStackState(new CenterState());
                playerI.setOnGround(playerJ.getY());
                playerI.setBottomPlayer(playerJ);
                //第j隻的狀態設為Bottom
                playerJ.setStackState(new BottomState());
            }
        //左右碰撞
        } else if (colPlayer == Global.ACT_PRESSED_LEFT || colPlayer == Global.ACT_PRESSED_RIGHT) {
            int offset = 0;
            if (colPlayer == Global.ACT_PRESSED_LEFT) {
                offset = playerJ.getRight() - playerI.getLeft();
            } else {
                offset = playerJ.getLeft() - playerI.getRight();
            }
            playerI.xStop(colPlayer, offset);
        }
    }

    private void playerCheckDefault(int colPlayer, Player playerI, Player playerJ){
        //下方碰撞 //第i隻在第j隻的頭上

        if (colPlayer == Global.ACT_PRESSED_DOWN) {
//            System.out.println("playerJ ");

            if(playerJ.getStackState() instanceof HeadState){
                playerJ.setStackState(new CenterState());

                playerI.setStackState(new HeadState());
                playerI.setOnGround(playerJ.getY());
                playerI.setBottomPlayer(playerJ);
            }else if(playerJ.getStackState() instanceof DefaultState){
//                System.out.println("Default 向下撞到 Default");
                //把第i隻的y設為j+height
                playerI.setStackState(new HeadState());
                playerI.setOnGround(playerJ.getY());
                playerI.setBottomPlayer(playerJ);
                //第j隻的狀態設為Bottom
                playerJ.setStackState(new BottomState());

            }

        //上方碰撞  //第j隻在第i隻的頭上
        } else if (colPlayer == Global.ACT_PRESSED_UP) {
//                        System.out.println("第" + i + "隻上方碰撞");
            //把第i隻設為Bottom
            playerI.setStackState(new BottomState());
            //第 j隻的狀態設為Head
            playerJ.setStackState(new HeadState());
            playerJ.setOnGround(playerI.getY());
            playerJ.setBottomPlayer(playerI);

        } else {
            int offset = 0;
            if (colPlayer == Global.ACT_PRESSED_LEFT) {
                offset = playerJ.getRight() - playerI.getLeft();
            } else {
                offset = playerJ.getLeft() - playerI.getRight();
            }
            playerI.xStop(colPlayer, offset);
        }
    }

    private void playersAllDead() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).dead();
        }
        arc.playSound(PathBuilder.getAudio(AudioPath.DEAD));
    }

    private boolean checkCenter(Player player, Tile tile ,int col){
        if (col == Global.ACT_PRESSED_LEFT || col == Global.ACT_PRESSED_RIGHT) {
            int offset = 0;
            if (col == Global.ACT_PRESSED_LEFT) {
                offset = tile.getRight() - player.getLeft();
            } else {
                offset = tile.getLeft() - player.getRight();
            }
            player.xStop(col, offset);
        }
        return false;
    }

    private boolean[] checkDefault(Player player, Tile tile, int col){
        boolean[] boos = {false, false};//isOnGround ,isDead
        if (col == Global.ACT_PRESSED_DOWN) {
            boos[0] = true;//isOnGround

            player.setOnGround(tile.getY());

            if (tile instanceof FloatBoard_H && player.getxState() instanceof StandState) {
                FloatBoard_H floatBoard = (FloatBoard_H) tile;

                player.setDirection(new OnFloatBoardState(floatBoard));

            }
            //踩錯地板顏色的死亡判斷
            if (!player.isDeadState() && player.isOnWrongColor(tile)) {
//                System.out.println("player wrong color tile" + player.getColor());
                playersAllDead();
                boos[1] = true;//isDead
                return boos;
//                return true;
            }
            //跳板
            if (tile instanceof JumpBoard) {
                player.jump(Global.Y_SPEED_DOUBLE);
            }
            //上方碰撞
        } else if (col == Global.ACT_PRESSED_UP) {
//            System.out.println("CheckDefault");
//            System.out.println("tile" + tile.getX() + tile.getY());
//            System.out.println("player" + player.getX() + player.getY());
//            System.out.println("player" + player.getColor() + " " + player.getLifeState().getClass().getName());
//            System.out.println("------------------------------------------");
            player.yStop(col);
            //左右碰撞
        } else if (col == Global.ACT_PRESSED_LEFT || col == Global.ACT_PRESSED_RIGHT) {
//            System.out.println("CheckDefault");
//            System.out.println("tile" + tile.getX() + tile.getY());
//            System.out.println("player" + player.getX() + player.getY());
            int offset = 0;
            if (col == Global.ACT_PRESSED_LEFT) {
                offset = tile.getRight() - player.getLeft();
//                System.out.println("offset" + offset);
            } else {
                offset = tile.getLeft() - player.getRight();
            }
            player.xStop(col, offset);
        }

        return boos;
    }

    private boolean[] checkBottom(Player player, Tile tile, int col) {
        boolean[] boos = {false, false};//isOnGround ,isDead

        if (col == Global.ACT_PRESSED_DOWN) {
            boos[0] = true;//isOnGround
            player.setOnGround(tile.getY());

            if (tile instanceof FloatBoard_H && player.getxState() instanceof StandState) {
                FloatBoard_H floatBoard = (FloatBoard_H) tile;
//                floatButton = ((FloatBoard_H) tile).getFloatButton();
//                System.out.println(player.getxState());
                player.setDirection(new OnFloatBoardState(floatBoard));
            }
            //踩錯地板顏色的死亡判斷
            if (!player.isDeadState() && player.isOnWrongColor(tile)) {
                playersAllDead();
                boos[1] = true;//isDead
                return boos;
            }
            //跳板
            if (tile instanceof JumpBoard) {
                player.jump(Global.Y_SPEED_DOUBLE);
            }
            //左右碰撞
        } else if (col == Global.ACT_PRESSED_LEFT || col == Global.ACT_PRESSED_RIGHT) {
            int offset = 0;
            if (col == Global.ACT_PRESSED_LEFT) {
                offset = tile.getRight() - player.getLeft();
            } else {
                offset = tile.getLeft() - player.getRight();
            }
            player.xStop(col, offset);
        }

        return boos;
    }

    private boolean checkHead(Player player, Tile tile, int col) {
        if (col == Global.ACT_PRESSED_UP) {
//            System.out.println("CheckHead");
//            System.out.println("tile" + tile.getX() + tile.getY());
//            System.out.println("player" + player.getX() + player.getY());
//            System.out.println("player" + player.getColor() + " " + player.getLifeState().getClass().getName());
            player.yStop(col);
            //左右碰撞
        } else if (col == Global.ACT_PRESSED_LEFT || col == Global.ACT_PRESSED_RIGHT) {

            int offset = 0;
            if (col == Global.ACT_PRESSED_LEFT) {
                offset = tile.getRight() - player.getLeft();
            } else {
                offset = tile.getLeft() - player.getRight();
            }
            player.xStop(col, offset);
        }
        return false;
    }

    @Override
    public void sceneEnd() {
        if(levelCount < 2){
            levelCount++;
        }
        sceneBegin();
    }

    @Override
    public void paint(Graphics g) {

        if(!popupWindow.getIsFinished()){
            popupWindow.paint(g);
        }else{
            camera.paint(g);
            cloud.paint(g);

            for (int i = 0; i < tiles.size(); i++) {
                camera.paint(g, tiles.get(i));
            }
            for (int i = 0; i < rebirthPairs.size(); i++) {
                camera.paint(g, rebirthPairs.get(i).getRebirth1());
                camera.paint(g, rebirthPairs.get(i).getRebirth2());
            }
            for (int i = 0; i < obstacles.size(); i++) {
                camera.paint(g, obstacles.get(i));
            }
            for (int i = 0; i < players.size(); i++) {
                camera.paint(g, players.get(i));
            }
            for (int i = 0; i < diamonds.size(); i++) {
                camera.paint(g, diamonds.get(i));
            }

            camera.paint(g, flag);
            scoreSystem.getTimerCounter().paint(g);
            scoreSystem.paint(g);
//            g.drawImage((modeNumber == 1 ? guideImg_1 : guideImg_2), levelCount, levelCount, null);
        }


    }

    @Override
    public KeyCommandListener getKeyCommandListener() {
//        System.out.println(players.get(0).isDeadState());
        if (players.get(0).isDeadState()) {
            return null;
        }
        if(!popupWindow.getIsFinished()){
            return popupWindow.getKeyCommandListener();
        }
        return keyCommandListener;
    }



}
