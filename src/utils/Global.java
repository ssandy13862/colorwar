/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author ghost
 */
public class Global {
    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 60;
    public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;

    //資料更新時間
    public static final int FRAME_LIMIT = 120; //畫面最大更新率:幀數提高就多動一點 幀數降低就少動一點
    public static final int LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;

    //物件速度計算
    public static final int ACT_SPEED = 90 / UPDATE_TIMES_PER_SEC;
    public static final int ANIMA_DELAY = 1 * (UPDATE_TIMES_PER_SEC / 15);
    public static final float ACCELERATION = 0.1f;
    public static final float G = 0.2f;
    public static final float Y_SPEED = - 5f;
    public static final float Y_SPEED_DOUBLE = - 8f;

    //圖片大小制定
    public static final int IMG_X_OFFSET = 40;
    public static final int IMG_Y_OFFSET = 40;

    //物件顏色
    public static final int COLOR_NONE = -1;
    public static final int COLOR_RED_0 = 0;
    public static final int COLOR_RED_1 = 21;
    public static final int COLOR_RED_2 = 22;
    public static final int COLOR_RED_3 = 23;
    public static final int COLOR_RED_4 = 24;
    public static final int COLOR_RED_5 = 25;
    public static final int COLOR_RED_6 = 26;
//    public static final int COLOR_RED_7 = 27;
//    public static final int COLOR_RED_8 = 28;
    public static final int COLOR_BLUE_0 = 1;
    public static final int COLOR_BLUE_1 = 11;
    public static final int COLOR_BLUE_2 = 12;
    public static final int COLOR_BLUE_3 = 13;
    public static final int COLOR_BLUE_4 = 14;
    public static final int COLOR_BLUE_5 = 15;
    public static final int COLOR_BLUE_6 = 16;
//    public static final int COLOR_BLUE_7 = 17;
//    public static final int COLOR_BLUE_8 = 18;
    public static final int COLOR_PURPLE_0 = 2;
    public static final int COLOR_PURPLE_1 = 7;
    public static final int COLOR_DARKPURPLE = 8;
    public static final int COLOR_BROWN = 3;
    public static final int COLOR_YELLOW_0 = 4;
    public static final int COLOR_YELLOW_1 = 5;
    public static final int COLOR_PINK = 6;
    public static final int COLOR_GREEN_0 = 9;
    public static final int COLOR_GREEN_1 = 10;
    public static final int COLOR_GREEN_2 = 30;
    



    //邊界參數
    public static final int JFRAME_WIDTH = 1060;
    public static final int JFRAME_HEIGHT = 640;


    //上下左右常數設定
    public static final int ACT_STOP = 0;
    public static final int ACT_PRESSED_RIGHT = 1;
    public static final int ACT_PRESSED_LEFT = 2;
    public static final int ACT_PRESSED_DOWN = 8;
    public static final int ACT_PRESSED_UP = 9;
    public static final int ACT_RELEASED_RIGHT = 3;
    public static final int ACT_RELEASED_LEFT = 4;
    public static final int ACT_JUMP = 6;
    public static final int ACT_ENTER = 7;
    public static final int ACT_CHANGE_PLAYER = 10;

    public static final int ACT_2_RIGHT = 11;
    public static final int ACT_2_LEFT = 12;
    public static final int ACT_2_JUMP = 13;
    public static final int ACT_RESTART = 14;
    public static final int ACT_ESC = 15;


    //跳躍的高度
    public static final int JUMP_HEIGHT = 160;
    
    //死亡模式設定
    public static final boolean DEAD_MODE = true;


}