/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.obstacle;

import gameobject.GameObject;

/**
 *
 * @author winniewang
 */
public abstract class Obstacle extends GameObject{

    public Obstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public void move(){}
}
