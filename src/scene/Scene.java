/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.SceneControllers;
import utils.CommandSolver.KeyCommandListener;
import java.awt.Graphics;
import javax.sound.sampled.Clip;
import utils.CommandSolver.TypedListener;

/**
 *
 * @author user1
 */
public abstract class Scene{
    protected SceneControllers sceneController;
    protected static Clip startAudioBG;
    
    public Scene(SceneControllers sceneController){
        this.sceneController = sceneController;
    }
    
    public abstract void sceneBegin();
    public abstract void sceneUpdate();
    public abstract void sceneEnd();
    public abstract void paint(Graphics g);
    public KeyCommandListener getKeyCommandListener(){return null;}
    public TypedListener getTypedListener(){return null;}
}
