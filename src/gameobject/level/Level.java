/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.level;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.obstacle.BlueWaveTwo;
import gameobject.obstacle.Monster;
import gameobject.player.Player;
import gameobject.prop.Flag;
import gameobject.obstacle.Obstacle;
import gameobject.obstacle.RedWaveTwo;
import gameobject.obstacle.Thron;
import gameobject.prop.Diamond;
import gameobject.prop.Rebirth;
import gameobject.prop.RebirthPair;
import gameobject.tile.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class Level {
    private BufferedImage img;

    private int[] rgb;
    private int[][] map;
    private int width;
    private int height;
    private int color;
    private ArrayList<Tile> tiles;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Player> players;
    private ArrayList<Rebirth> rebirths;
    private ArrayList<Diamond> diamonds;
    private ArrayList<RebirthPair> rebirthPairs;
    private Flag flag;
//    private int countRed;
//    private int countBlue;

    public Level(String path){
        ImageResourceController irc = ImageResourceController.getInstance();
        this.img = irc.tryGetImage(PathBuilder.getImg(path));
        rgb = new int[3];
        width = img.getWidth() / 40;
        height = img.getHeight() / 40;
//        System.out.println(width+" "+height);
        map = new int[height][width];
        tiles = new ArrayList<>();
        obstacles = new ArrayList<>();
        players = new ArrayList<>();
        rebirths = new ArrayList<>();
        diamonds = new ArrayList<>();
        rebirthPairs = new ArrayList<>();
//        countRed = 0;
//        countBlue = 2;

//        flag = new Flag(480, 380, 40, 40);
//        players.add(new Player(0, 200, 40, 40, 3, 0));
//        players.add(new Player(0, 460, 40, 40, 3, 1));
//        obstacles.add(new Monster(40, 120, 40, 39));
//        tiles.add(new FloatBoard_H(40, 380, 120, 20));


        //天花板
        for(int i = 0; i < 104; i++){
            tiles.add(new TileGround(i * 40, -40, 40, 40, 0));
        }
        toRGB(img);
    }

    public void toRGB(BufferedImage img){
        for(int column = 0; column < width; column++){
            for(int row = 0; row < height; row++){
                map[row][column] = img.getRGB(column, row);
                int pixel = img.getRGB(column * 40+20, row * 40+20);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
//                System.out.println(row + " " + column + ":("+rgb[0]+","+rgb[1]+","+rgb[2]+")");
                color = pixelToTile(rgb[0], rgb[1], rgb[2], row * 40, column * 40);
                switchTile(color, row * 40, column * 40);
            }
        }
    }

    public int pixelToTile(int r, int g, int b, int row, int col){

        if(r == 255 && g == 255 && b == 255){
            return Global.COLOR_NONE;
        }

        if(r == 67 && g == 33 && b == 8){
            return Global.COLOR_BROWN;          //地板
        }

        if(r == 103 && g == 40 && b == 143){
            return Global.COLOR_PURPLE_0;       //尖刺
        }

        if(r == 41 && g == 0 && b == 20){
            return Global.COLOR_PURPLE_1;       //怪物
        }

        //紅色模組
        if(r == 239 && g == 89 && b == 16){
            return Global.COLOR_RED_0;          //前面
        }
        if(r == 236 && g == 22 && b == 23){
            return Global.COLOR_RED_1;          //中間
        }
        if(r == 191 && g == 37 && b == 39){
            return Global.COLOR_RED_2;          //後面
        }
        if(r == 156 && g == 0 && b == 93){
            return Global.COLOR_RED_3;          //獨立
        }
        if(r == 219 && g == 77 && b == 81){
            return Global.COLOR_RED_5;          //紅角
        }
        if(r == 255 && g == 128 && b == 77){
            return Global.COLOR_RED_6;          //紅海帶
        }
        if(r == 255 && g == 0 && b == 186){
//            rebirths.add(new Rebirth(col, row, 40, 40, 0));
            Rebirth rebirth1 = new Rebirth(col, row, 40, 40, 0);
            RebirthPair rebirthPair = new RebirthPair();
            rebirthPair.setRebirth1(rebirth1);
            rebirthPairs.add(rebirthPair);
//            return Global.COLOR_RED_4;          //紅復活0
        }
        if(r == 255 && g == 1 && b == 186){
            Rebirth rebirth1 = new Rebirth(col, row, 40, 40, 0);
            RebirthPair rebirthPair = new RebirthPair();
            rebirthPair.setRebirth1(rebirth1);
            rebirthPairs.add(rebirthPair);
//            return Global.COLOR_RED_4;          //紅復活1
        }
        if(r == 255 && g == 2 && b == 186){
            Rebirth rebirth1 = new Rebirth(col, row, 40, 40, 0);
            RebirthPair rebirthPair = new RebirthPair();
            rebirthPair.setRebirth1(rebirth1);
            rebirthPairs.add(rebirthPair);
//            return Global.COLOR_RED_4;          //紅復活2
        }
        if(r == 255 && g == 3 && b == 186){
            Rebirth rebirth1 = new Rebirth(col, row, 40, 40, 0);
            RebirthPair rebirthPair = new RebirthPair();
            rebirthPair.setRebirth1(rebirth1);
            rebirthPairs.add(rebirthPair);
//            return Global.COLOR_RED_4;          //紅復活1
        }
        if(r == 255 && g == 4 && b == 186){
            Rebirth rebirth1 = new Rebirth(col, row, 40, 40, 0);
            RebirthPair rebirthPair = new RebirthPair();
            rebirthPair.setRebirth1(rebirth1);
            rebirthPairs.add(rebirthPair);
//            return Global.COLOR_RED_4;          //紅復活2
        }

        //藍色模組
        if(r == 0 && g == 255 && b == 255){
            return Global.COLOR_BLUE_0;         //前面
        }
        if(r == 28 && g == 166 && b == 224){
            return Global.COLOR_BLUE_1;         //中間
        }
        if(r == 0 && g == 111 && b == 187){
            return Global.COLOR_BLUE_2;         //後面
        }
        if(r == 45 && g == 45 && b == 144){
            return Global.COLOR_BLUE_3;         //獨立
        }
        if(r == 50 && g == 89 && b == 191){
            return Global.COLOR_BLUE_5;         //藍角
        }
        if(r == 24 && g == 181 && b == 181){
            return Global.COLOR_BLUE_6;         //藍海帶
        }
        if(r == 0 && g == 181 && b == 175){
            Rebirth rebirth2 = new Rebirth(col, row, 40, 40, 1);
            rebirthPairs.get(0).setRebirth2(rebirth2);
//            return Global.COLOR_BLUE_4;         //藍復活0
        }
        if(r == 1 && g == 181 && b == 175){
            Rebirth rebirth2 = new Rebirth(col, row, 40, 40, 1);
            rebirthPairs.get(1).setRebirth2(rebirth2);
//            return Global.COLOR_BLUE_4;         //藍復活1
        }
        if(r == 2 && g == 181 && b == 175){
            Rebirth rebirth2 = new Rebirth(col, row, 40, 40, 1);
            rebirthPairs.get(2).setRebirth2(rebirth2);
//            return Global.COLOR_BLUE_4;         //藍復活2
        }
        if(r == 3 && g == 181 && b == 175){
            Rebirth rebirth2 = new Rebirth(col, row, 40, 40, 1);
            rebirthPairs.get(3).setRebirth2(rebirth2);
//            return Global.COLOR_BLUE_4;         //藍復活3
        }
        if(r == 4 && g == 181 && b == 175){
            Rebirth rebirth2 = new Rebirth(col, row, 40, 40, 1);
            rebirthPairs.get(4).setRebirth2(rebirth2);
//            return Global.COLOR_BLUE_4;         //藍復活4
        }


        if(r == 255 && g == 255 && b == 0){
            return Global.COLOR_YELLOW_0;       //跳板
        }

        if(r == 255 && g == 121 && b == 168){
            return Global.COLOR_PINK;           //得分
        }

        if(r == 0 && g == 143 && b == 66){
            return Global.COLOR_GREEN_0;        //旗子
        }

        if(r == 0 && g == 196 && b == 53){
            return Global.COLOR_GREEN_1;        //水平浮板
        }

        if(r == 0 && g == 228 && b == 48){
            return Global.COLOR_GREEN_2;        //垂直浮板
        }
        return -10;
    }


    public void switchTile(int color, int row, int col){
        switch(color){
            case Global.COLOR_NONE:
                break;
            case Global.COLOR_RED_0:
                tiles.add(new TileRed(col, row, 40, 40, 0));
                break;
            case Global.COLOR_RED_1:
                tiles.add(new TileRed(col, row, 40, 40, 1));
                break;
            case Global.COLOR_RED_2:
                tiles.add(new TileRed(col, row, 40, 40, 2));
                break;
            case Global.COLOR_RED_3:
                tiles.add(new TileRed(col, row, 40, 40, 3));
                break;
            case Global.COLOR_RED_4:



                break;
            case Global.COLOR_RED_5:
                players.add(new Player(col, row, 40, 40, 3, 0));
                break;
            case Global.COLOR_RED_6:
                obstacles.add(new RedWaveTwo(col + 3, row + 1, 78, 38));
                break;
            case Global.COLOR_BLUE_0:
                tiles.add(new TileBlue(col, row, 40, 40, 0));
                break;
            case Global.COLOR_BLUE_1:
                tiles.add(new TileBlue(col, row, 40, 40, 1));
                break;
            case Global.COLOR_BLUE_2:
                tiles.add(new TileBlue(col, row, 40, 40, 2));
                break;
            case Global.COLOR_BLUE_3:
                tiles.add(new TileBlue(col, row, 40, 40, 3));
                break;
            case Global.COLOR_BLUE_4:
//                rebirths.add(new Rebirth(col, row, 40, 40, 1));
                break;
            case Global.COLOR_BLUE_5:
                players.add(new Player(col, row, 40, 40, 3, 1));
                break;
            case Global.COLOR_BLUE_6:
                obstacles.add(new BlueWaveTwo(col + 3, row + 1, 78, 38));
                break;
            case Global.COLOR_BROWN:
                int num = (int)(Math.random() * 30);
                if(num < 27){
                    num = 0;
                }else if(num == 28){
                    num = 1;
                }else{
                    num = 2;
                }
                tiles.add(new TileGround(col, row, 40, 40, num));
                break;
            case Global.COLOR_YELLOW_0:
                tiles.add(new SmallJumpBoard(col, row, 80, 40));
                break;
            case Global.COLOR_PURPLE_0:
                obstacles.add(new Thron(col, row + 20, 40, 40));
                break;
            case Global.COLOR_PURPLE_1:
                obstacles.add(new Monster(col, row, 40, 39));
                break;
            case Global.COLOR_PINK:
                diamonds.add(new Diamond(col+10, row+10, 40, 40));
                break;
            case Global.COLOR_GREEN_0:
                flag = new Flag(col, row, 40, 40);
                break;
            case Global.COLOR_GREEN_1:
                tiles.add(new FloatBoard_H(col, row, 80, 20));
                break;
            case Global.COLOR_GREEN_2:
                tiles.add(new FloatBoard_V(col, row, 80, 20));
                break;
            default:
                break;
        }

    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

//    public ArrayList<Rebirth> getRebirth(){
//        return rebirths;
//    }

    public ArrayList<RebirthPair> getRebirthPairs(){
        return rebirthPairs;
    }

    public ArrayList<Diamond> getDiamonds(){
        return diamonds;
    }


    public Flag getflag(){
        return flag;
    }

    public int getWidth(){
        return img.getWidth();
    }

}
