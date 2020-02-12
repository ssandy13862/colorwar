/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.level;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.player.Player;
import gameobject.prop.Flag;
import gameobject.obstacle.Obstacle;
import gameobject.obstacle.Thron;
import gameobject.prop.Diamond;
import gameobject.prop.Rebirth;
import gameobject.tile.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author ghost
 */
public class LevelFor2 {
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
    private Flag flag;

    public LevelFor2(String path){
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
        flag = new Flag(4080, 440, 40, 40);             //寫進顏色判斷
        players.add(new Player(0, 200, 40, 40, 3, 0)); //寫進顏色判斷
        players.add(new Player(80, 200, 40, 40, 3, 0));
        players.add(new Player(0, 460, 40, 40, 3, 1));
        players.add(new Player(80, 460, 40, 40, 3, 1));
        for(int i = 0; i < 104; i++){
            tiles.add(new TileGround(i*40, -40, 40, 40, 0));
        }
        toRGB(img);
    }

    public void toRGB(BufferedImage img){
        for(int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                map[row][column] = img.getRGB(column, row);
                int pixel = img.getRGB(column * 40+20, row * 40+20);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
//                System.out.println(row + " " + column + ":("+rgb[0]+","+rgb[1]+","+rgb[2]+")");
                color = pixelToTile(rgb[0], rgb[1], rgb[2]);
                switchTile(color, row * 40, column * 40);
            }
        }
    }

    //少了玩家/旗子 //浮動板/怪物/寶石/重生點
    public int pixelToTile(int r, int g, int b){

        if(r > 250 && g > 250 && b > 250){
//            System.out.println("white");
            return Global.COLOR_NONE;
        }

        //ground && thron
        if(60 <= r && r <= 120 && 30 <= g && g <= 60){
            if(b <= 20){
//                System.out.println("brown");
                return Global.COLOR_BROWN;

            }
            if(140 <= b && b <= 150){
//                System.out.println("thron");
                return Global.COLOR_PURPLE_0;
            }
        }

        //紅色模組
        if(150 <= r && 15 <= b && b <= 95){
            if(80 <= g && g <= 90){
//                System.out.println("red");  //前面
                return Global.COLOR_RED_0;
            }
            if(20 <= g && g <= 30){
//                System.out.println("red");  //中間
                return Global.COLOR_RED_1;
            }
            if(30 <= g && g <= 40){
//                System.out.println("red");  //後面
                return Global.COLOR_RED_2;

            }
            if(0 == g){
//                System.out.println("red");  //獨立
                return Global.COLOR_RED_3;
            }

        }
        //藍色模組
        if(r < 50 && 145 < b){
            if(250 < g){
//                System.out.println("blue");
                return Global.COLOR_BLUE_0; //前面
            }
            if(170 <= g && g <= 175 ){
//                System.out.println("blue");
                return Global.COLOR_BLUE_1; //中間
            }
            if(110 <= g && g <= 130){
//                System.out.println("blue");
                return Global.COLOR_BLUE_2; //後面
            }
            if(45 <= g && g <= 50){
//                System.out.println("blue");
                return Global.COLOR_BLUE_3; //獨立
            }

        }

        //跳板
        if(250 < r && 230 < g){
            if(30 <= b && b <= 35){
//                System.out.println("LongJumpB");
                return Global.COLOR_YELLOW_0;
            }
            if(0 <= b && b <= 5){
//                System.out.println("ShortJumpB");
                return Global.COLOR_YELLOW_0;
            }
        }
        if(250 < r && r < 256 && 120 <= g && g <= 130 && 170 <= b &&  b <= 175){
//            System.out.println("bonus");
            return Global.COLOR_PINK;
        }
        if(250 < r && r < 256 && 180 <= g && g <= 190 && 180 <= b && b <= 195){
//            System.out.println("紅復活點");
            return Global.COLOR_RED_4;
        }
        if(110 < r && r < 120 && 180 <= g && g <= 190 && 170 <= b && b <= 190){
//            System.out.println("藍復活點");
            return Global.COLOR_BLUE_4;
        }
        if(5 < r && 140 <= g && g <= 150 && b <= 60 && b <= 70){
//            System.out.println("旗子");
            return Global.COLOR_GREEN_0;
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
                rebirths.add(new Rebirth(col, row, 40, 40, 0));
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
                rebirths.add(new Rebirth(col, row, 40, 40, 1));
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
            case Global.COLOR_PINK:
                diamonds.add(new Diamond(col, row, 40, 40));
                break;
            case Global.COLOR_GREEN_0:
//                flag = new Flag(col, row, 40, 40);
                System.out.println(flag);
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
    
    public ArrayList<Rebirth> getRebirth(){
        return rebirths;
    }
    
    public ArrayList<Diamond> getDiamond(){
        return diamonds;
    }

    public Flag getflag(){
        return flag;
    }

    public int getWidth(){
        return img.getWidth();
    }

}
