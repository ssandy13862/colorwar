package midgameproject;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controllers.SceneControllers;
import java.awt.Graphics;
import javax.swing.JPanel;
import scene.StartScene;
import scene.StartScene.NormalAnima;
import utils.CommandSolver.CommandWrapper;

/**
 *
 * @author ghost
 */
public class GameJPanel extends JPanel{
    private SceneControllers sceneController;

    public GameJPanel(){
        sceneController = new SceneControllers();
        sceneController.changeScene(new StartScene(sceneController, new NormalAnima()));
    }

    @Override
    public void paintComponent(Graphics g){
        sceneController.paint(g);
    }

    public void update(CommandWrapper commands){
        sceneController.sceneUpdate(commands);
    }
}
