/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.obstacle;

import gameobject.tile.Tile;
import java.awt.Graphics;

/**
 *
 * @author winniewang
 */
public abstract class Wave extends Obstacle{

    public Wave(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
}
