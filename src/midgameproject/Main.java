/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midgameproject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import utils.CommandSolver;
import utils.FontManager;
import utils.Global;

/**
 *
 * @author ghost
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JFrame j = new JFrame();
        j.setSize(Global.JFRAME_WIDTH, Global.JFRAME_HEIGHT);
        j.setTitle("ColorWar");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameJPanel jp = new GameJPanel();
        j.add(jp);

        //
        CommandSolver commandSolver = new CommandSolver.Builder(Global.MILLISEC_PER_UPDATE * 2, new int[][]{
            { KeyEvent.VK_RIGHT, Global.ACT_PRESSED_RIGHT },
            { KeyEvent.VK_LEFT, Global.ACT_PRESSED_LEFT },
            { KeyEvent.VK_Z, Global.ACT_CHANGE_PLAYER},
            { KeyEvent.VK_SPACE, Global.ACT_JUMP},
            { KeyEvent.VK_UP, Global.ACT_JUMP},
            { KeyEvent.VK_R, Global.ACT_RESTART},
            { KeyEvent.VK_ENTER, Global.ACT_ENTER},
            { KeyEvent.VK_A, Global.ACT_2_LEFT},
            { KeyEvent.VK_D, Global.ACT_2_RIGHT},
            { KeyEvent.VK_W, Global.ACT_2_JUMP},
            { KeyEvent.VK_ESCAPE, Global.ACT_ESC},
        }).keyTypedMode().trackChar().gen();

        j.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyevent) {
              }

            @Override
            public void keyPressed(KeyEvent keyevent) {
                commandSolver.trig(keyevent.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent keyevent) {
                commandSolver.trig(keyevent.getKeyCode(), false);
            }
        });
        j.setFocusable(true);
        commandSolver.start();
        j.setVisible(true);
        FontManager.getInstance().preLoading(jp.getGraphics());

        //while迴圈

        long startTime = System.currentTimeMillis();
        long updateTime = System.currentTimeMillis();
        long passedFrames = 0;
        while(true){
            long currentTime = System.currentTimeMillis();
            long passedTime = currentTime - startTime;
            long targetFrame = passedTime / Global.MILLISEC_PER_UPDATE;

            while(passedFrames < targetFrame){
                jp.update(commandSolver.update());
                passedFrames++;
            }

            if(Global.LIMIT_DELTA_TIME < currentTime - updateTime){
                updateTime = currentTime;
                j.repaint();
            }
        }
    }

}
