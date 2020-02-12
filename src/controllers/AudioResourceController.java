/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author winniewang
 */
public class AudioResourceController {
    
    private class KeyPair{
        private AudioClip audio;
        private String path;
        
        public KeyPair(AudioClip audio, String path){
            this.audio = audio;
            this.path = path;
        }
    }
    private static AudioResourceController arc;
    
    
    private AudioResourceController(){
        sounds = new HashMap<>();
    }
    
    public static AudioResourceController getInstance(){
        if(arc == null){
            arc = new AudioResourceController();
        }
        return arc;
    }
    
    private HashMap<String, Long> sounds;
    private static final int timeInterval = 100;
    
    public Clip getSound(String path){
        long currentTime = System.currentTimeMillis();
        if(sounds.containsKey(path)){
            long lastTime = sounds.get(path);
            if(currentTime - lastTime < timeInterval){
                return null;
            }
        }
        
        URL url = getClass().getResource(path);
        File file = new File(url.getPath());
        
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            sounds.put(path, currentTime);
            return clip;
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioResourceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void playSound(String path){
        Clip clip = getSound(path);
        if(clip != null){
            clip.start();
        }
    }
}
