/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.tile;

import gameobject.GameObject;
import java.awt.Graphics;


/**
 *
 * @author winniewang
 */
public abstract class Tile extends GameObject{

    public Tile(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    
    public abstract int getColor();
    
    public void move(){
    }
    
    public abstract void paint(Graphics g, int x, int y);
}
