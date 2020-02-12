/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.player;

import controllers.AudioResourceController;
import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import gameobject.MovableGameObject;
import gameobject.tile.FloatBoard_H;
import gameobject.tile.Tile;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.Clip;
import utils.DelayCounter;
import utils.Global;
import values.AudioPath;
import values.ImagePath;

/**
 *
 * @author winniewang
 */
public class Player extends MovableGameObject {

    //人物移動的狀態 向左、向右、靜止
    public interface MotionState {
        public abstract void move(Player player);
    }

    public static class MoveRightState implements MotionState {

        @Override
        public void move(Player player) {
            if (player.xSpeed < player.getSpeedCount()) {
                player.xSpeed += Global.ACCELERATION;
            }
        }
    }

    public static class MoveLeftState implements MotionState {

        @Override
        public void move(Player player) {
            if (player.xSpeed > - player.getSpeedCount()) {
                player.xSpeed -= Global.ACCELERATION;
            }
        }
    }

    public static class StandState implements MotionState {

        @Override
        public void move(Player player) {
            if (player.xSpeed > 0) {
                player.xSpeed -= Global.ACCELERATION / 2;
            } else if (player.xSpeed < 0) {
                player.xSpeed += Global.ACCELERATION / 2;
            }
            if (Math.abs(player.xSpeed) < 0.1) {
                player.xSpeed = 0;
            }
        }
    }

    //人物跳躍的狀態 地板、跳躍中
    //跳躍的向上初速度 -20 會一直加地心引力，到碰到地板後ySpeed設成0
    public static class JumpState implements MotionState {

        @Override
        public void move(Player player) {
            player.ySpeed += Global.G;
        }

    }

    public static class OnGroundState implements MotionState {

        @Override
        public void move(Player player) {
            player.ySpeed = 0;
        }

    }
    
    //浮版
    public static class OnFloatBoardState implements MotionState {

        private FloatBoard_H floatBoard;
        
        public OnFloatBoardState(FloatBoard_H floatBoard){
            this.floatBoard = floatBoard;
        }
        
        @Override
        public void move(Player player) {
            player.xSpeed = floatBoard.getxSpeed();
        }
       
    }
    
    public interface StackState{
        public void action(Player player);
    }
    
    public static class HeadState implements StackState {
        
        @Override
        public void action(Player player) {
//            System.out.println("HeadState bottomPlayer" + bottomPlayer);
            if(player.getxState() instanceof MoveRightState || player.getxState() instanceof MoveLeftState){
                return;
            }
//            System.out.println("HeadState bottomPlayer" + player.bottomPlayer);
            player.xSpeed = player.bottomPlayer.xSpeed;
//            player.xState = bottomPlayer.xState;
        }
    }
    
    public static class CenterState implements StackState {
        
        @Override
        public void action(Player player) {
//            System.out.println("Center State bottomPlayer" + bottomPlayer);
            if(player.getxState() instanceof MoveRightState || player.getxState() instanceof MoveLeftState){
                return;
            }
//            System.out.println("CenterState bottomPlayer" + player.bottomPlayer);
            player.xSpeed = player.bottomPlayer.xSpeed;
//            player.xState = bottomPlayer.xState;
        }
    }
    
    public static class BottomState implements StackState {

        @Override
        public void action(Player player) {
            
        }
        
    }
    
    public static class DefaultState implements StackState {

        @Override
        public void action(Player player) {
            
        }
        
    }

    public interface LifeState {

        public abstract void execute(Player player);
    }

    public static class AliveState implements LifeState {

        @Override
        public void execute(Player player) {
            player.stackState.action(player);
            player.xState.move(player);
            player.yState.move(player);

            player.x = (int)Math.round(player.x + player.xSpeed);
            player.y += player.ySpeed;
            

            if (player.xSpeed > 0) {
                player.direction = 1;
            } else if (player.xSpeed < 0) {
                player.direction = 2;
            } else {
                player.direction = 0;
            }
        }
    }

    //死亡
    //1.回到復活點 2.切到活著模式
    public static class DeadState implements LifeState {

        @Override
        public void execute(Player player) {
            player.yState.move(player);
            player.y += player.ySpeed;
//            System.out.println("enter deadstate");

//            System.out.println("y" + player.y + "player.deadY" + player.deadY);
            if(player.y >= player.deadY){
//                System.out.println("rebirth");
                player.x = player.rebirthX;
                player.y = player.rebirthY;
//                System.out.println("player.x" + player.x);
//                System.out.println("player.y" + player.y);
                player.color = player.rebirthColor;
                player.playerPosition = 160 * player.color;
//                System.out.println("rebirthColor" + " " + player.rebirthColor);
                player.lifeState = new AliveState();
                player.xState = new StandState();
            }
        }
    }

    private BufferedImage img;
//    private BufferedImage boom;
    private int act;
    private int AnimaAct;
    private DelayCounter delayCounter;
    private DelayCounter animaDelayCounter;
    private int jumpAct;
    private int playerPosition;
    private int color;
    private float xSpeed;
    private float ySpeed;
    private int direction;
    private MotionState xState;
    private MotionState yState;
    private LifeState lifeState;
    private StackState stackState;
//    private Rebirth rebirth;
    private int rebirthX;
    private int rebirthY;
    private int rebirthColor;
    private int deadY;
    private Clip audioJump;
    private Player bottomPlayer;
    private AudioResourceController arc;
//    private boolean boomAnima;

    public Player(int x, int y, int width, int height, int speedCount, int color) {
        super(x, y, width, height, speedCount);
        ImageResourceController irc = ImageResourceController.getInstance();
        img = irc.tryGetImage(PathBuilder.getImg(ImagePath.PLAYER));
//        boom = irc.tryGetImage(PathBuilder.getImg(ImagePath.BOOM));
        arc = AudioResourceController.getInstance();
        jumpAct = act = AnimaAct = 0;
        delayCounter = new DelayCounter(2);
        animaDelayCounter = new DelayCounter(3);
        ySpeed = xSpeed = 0;
        direction = 0;
        this.color = color;
        this.rebirthColor = color;
        playerPosition = 160 * color;
        xState = new StandState();
        yState = new OnGroundState();
        lifeState = new AliveState();
        stackState = new DefaultState();
        rebirthX = x;
        rebirthY = y;
//        boomAnima = false;

    }

    public int getRebirthColor(){
        return rebirthColor;
    }

    public boolean isDeadState(){
        if(this.lifeState instanceof DeadState){
            return true;
        }
        return false;
    }
    
    public StackState getStackState(){
        return stackState;
    }

    public void dead(){
//        System.out.println("player dead");
        this.deadY = this.y;
        this.direction = 4;
        this.xSpeed = 0;
        this.ySpeed = Global.Y_SPEED;
        this.yState = new JumpState();
        this.lifeState = new DeadState();
//        System.out.println(lifeState);
    }
    
    public MotionState getxState(){
        return xState;
    }

//    //浮板
//    public void toFloatMove(boolean floatBoard){
//        if(floatBoard){
//            x--;
//        }else{
//            x++;
//        }
//    }

//    public void setBoomAnima(){
//        boomAnima = true;
//    }
    
    public void setStackState(StackState ss){
        this.stackState = ss;
    }
    public Player getBottomPlayer(){
        return bottomPlayer;
    }

    public void setRebirthX(int x){
        rebirthX = x;
    }

    public void setRebirthY(int y){
        rebirthY = y;
    }

    public void setLiveState(LifeState ls){
        this.lifeState = ls;
    }

    public void setDirection(MotionState ms) {
        this.xState = ms;
    }

    public void fall(){
        if (this.yState instanceof OnGroundState) {
            this.ySpeed += 3;
            this.yState = new JumpState();
        }
    }

    public void jump(float startYSpeed) {
        if (this.yState instanceof OnGroundState) {
            this.ySpeed = startYSpeed;
            this.yState = new JumpState();
            arc.playSound(PathBuilder.getAudio(AudioPath.JUMP));
        }
    }

    public float getYSpeed(){
        return ySpeed;
    }

    @Override
    public void move() {

        if (delayCounter.update()) {
            act = ++act % 4;
        }
//        if (animaDelayCounter.update()) {
//            AnimaAct = ++AnimaAct % 4;
//        }
        lifeState.execute(this);
    }

    public LifeState getLifeState(){
        return lifeState;
    }

    public void setBottomPlayer(Player BottomPlayer){
        this.bottomPlayer = BottomPlayer;
    }

//    public void transformAnimation() {
//        if (animaDelayCounter.update()) {
//            AnimaAct = ++AnimaAct % 4;
//        }
//    }

    public void paintBigger(Graphics g){
        g.drawImage(img, x, y, x + 45, y + 45,
                playerPosition + act * Global.IMG_X_OFFSET, direction * Global.IMG_Y_OFFSET,
                playerPosition + 40 + act * Global.IMG_X_OFFSET, 40 + direction * Global.IMG_Y_OFFSET, null);
    }

    @Override
    public void paint(Graphics g) {
//        g.drawImage(img, x, y, x + 40, y + 40,
//                playerPosition + act * Global.IMG_X_OFFSET, direction * Global.IMG_Y_OFFSET,
//                playerPosition + 40 + act * Global.IMG_X_OFFSET, 40 + direction * Global.IMG_Y_OFFSET, null);

    }

    public void paint(Graphics g, int x, int y) {
//        if(!boomAnima){
        g.drawImage(img, x, y, x + 40, y + 40,
                playerPosition + act * Global.IMG_X_OFFSET, direction * Global.IMG_Y_OFFSET,
                playerPosition + 40 + act * Global.IMG_X_OFFSET, 40 + direction * Global.IMG_Y_OFFSET, null);
//        }
//        if(boomAnima){
//        g.drawImage(boom, x, y, x + 40, y + 40,
//                AnimaAct * Global.IMG_X_OFFSET, 0,
//                40 + AnimaAct * Global.IMG_X_OFFSET, 40, null);
////                System.out.println("Anima"+boomAnima);
//                if(AnimaAct == 3){
//                    boomAnima = false;
//                }
////            System.out.println("boomAnima"+boomAnima);
////            System.out.println("---------------------------");
//        }
    }

    public boolean isOnWrongColor(GameObject obj) {
//        System.out.println("p color:" + this.color);
//        System.out.println("tile.getColor()" + obj.getColor());
        if (obj.getColor() != Global.COLOR_NONE && color != obj.getColor()) {
            return true;
        }
        return false;
    }
    
    Map<Integer, Integer> mapP = new HashMap();
    public int checkPlayerCollision(GameObject obj) {
        if (!isCollision(obj)) {
            return -1;
        }
        // check vertical | horizontal
        mapP.put(Global.ACT_PRESSED_LEFT, obj.getRight() - getLeft());
        mapP.put(Global.ACT_PRESSED_RIGHT, getRight() - obj.getLeft());
        mapP.put(Global.ACT_PRESSED_DOWN, getBottom() - obj.getTop());
        mapP.put(Global.ACT_PRESSED_UP, obj.getBottom() - getTop());
        

        int min = Global.ACT_PRESSED_DOWN;
//        System.out.println("floor distance"+ map.get(min));
        if(mapP.get(min) > mapP.get(Global.ACT_PRESSED_UP)){
            min = Global.ACT_PRESSED_UP;
        }
        if(mapP.get(min) > mapP.get(Global.ACT_PRESSED_LEFT)){
            min = Global.ACT_PRESSED_LEFT;
        }
        if(mapP.get(min) > mapP.get(Global.ACT_PRESSED_RIGHT)){
            min = Global.ACT_PRESSED_RIGHT;
        }

        // 縮小地面判定的左右範圍
        if(min == Global.ACT_PRESSED_DOWN){
            if(mapP.get(Global.ACT_PRESSED_LEFT) < 3.1 ||
                    mapP.get(Global.ACT_PRESSED_LEFT) > obj.getRight() - obj.getLeft() + this.width - 3.1){
                return -1;
            }
        }
        
        // 縮小上面判定的左右範圍
        if(min == Global.ACT_PRESSED_UP){
            if(mapP.get(Global.ACT_PRESSED_LEFT) < 3.1 ||
                    mapP.get(Global.ACT_PRESSED_LEFT) > obj.getRight() - obj.getLeft() + this.width - 3.1){
                return -1;
            }
        }
        return min;
    }

    Map<Integer, Integer> map = new HashMap();
    public int checkCollisionDirection(GameObject obj) {
        if (!isCollision(obj)) {
            return -1;
        }
        // check vertical | horizontal
        map.put(Global.ACT_PRESSED_LEFT, obj.getRight() - getLeft());
        map.put(Global.ACT_PRESSED_RIGHT, getRight() - obj.getLeft());
        map.put(Global.ACT_PRESSED_DOWN, getBottom() - obj.getTop());
        map.put(Global.ACT_PRESSED_UP, obj.getBottom() - getTop());
        
//        System.out.println("LEFT " + (obj.getRight() - getLeft()));
//        System.out.println("RIGHT " + (getRight() - obj.getLeft()));
//        System.out.println("DOWN " + (getBottom() - obj.getTop()));
//        System.out.println("UP " + (obj.getBottom() - getTop()));

        int min = Global.ACT_PRESSED_DOWN;
//        System.out.println("floor distance"+ map.get(min));
        if(map.get(min) > map.get(Global.ACT_PRESSED_LEFT)){
            min = Global.ACT_PRESSED_LEFT;
        }
        if(map.get(min) > map.get(Global.ACT_PRESSED_RIGHT)){
            min = Global.ACT_PRESSED_RIGHT;
        }
        if(map.get(min) > map.get(Global.ACT_PRESSED_UP)){
            min = Global.ACT_PRESSED_UP;
        }

        // 縮小地面判定的左右範圍
        if(min == Global.ACT_PRESSED_DOWN){
            if(map.get(Global.ACT_PRESSED_LEFT) < 3.1 ||
                    map.get(Global.ACT_PRESSED_LEFT) > obj.getRight() - obj.getLeft() + this.width - 3.1){
                return -1;
            }
        }
        return min;
    }

    public void setOnGround(int fix) {
        this.yState = new OnGroundState();
        this.ySpeed = 0;
        this.y = fix - height;
    }

    public void xStop(int collistionDirection, int offset){
        //人物x座標設為6
        //人物向左移動碰到障礙物
        //左鍵 xSpeed = - 0.1
        //人物.move() => x = round(x + xSpeed) 若不四捨五入 人物的x座標變成5.9會被int成5
        //判斷碰撞左邊
        //xSpeed = 0
        this.x += offset;
//        System.out.println("player x: " + x);
        if(direction == 1 && xSpeed > 0 && collistionDirection == 1
                || direction == 2 && xSpeed < 0 && collistionDirection == 2){
//            System.out.println("~");
//            System.out.println("~direction" + direction);
//            System.out.println("~xSpeed" + xSpeed);
//            System.out.println("~collistionDirection" + collistionDirection);
            
            this.xSpeed = 0;
        }
    }

    public void yStop(int collistionDirection){
//        System.out.println("yStop ySpeed before" + ySpeed);
        if(ySpeed < 0 && collistionDirection == Global.ACT_PRESSED_UP){
//            System.out.println("yStop ySpeed" + ySpeed);
            this.ySpeed = 0;

        }
        if(ySpeed == 0 && collistionDirection == Global.ACT_PRESSED_UP && bottomPlayer != null){
            if(stackState instanceof HeadState || stackState instanceof CenterState){
                this.bottomPlayer.ySpeed = 0;
                this.bottomPlayer.y += 2;
            }
            
        }
    }

    public float getxSpeed(){
        return xSpeed;
    }

    @Override
    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
        this.playerPosition = 160 * color;
    }

    public String toString(){
        return "color" + color + "lifestate" + lifeState + "yState" + yState;
    }

}
