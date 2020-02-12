/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author winniewang
 */
public class PathBuilder {
    private static final String ROOT = "/resources";
    private static final String IMG_ROOT = "/imgs";    
    private static final String AUDIO_ROOT = "/audios";
    //PathController.getImg(ImageResourcePath.AIRPLANE);
    
    public static String getImg(String path){
        return ROOT + IMG_ROOT + path;
    }
    
    public static String getAudio(String path){
        return ROOT + AUDIO_ROOT + path;
    }
}
