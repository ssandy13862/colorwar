/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.prop;

/**
 *
 * @author ghost
 */
public class RebirthPair {
    private Rebirth rebirth1;
    private Rebirth rebirth2;
    
    
    public boolean getIsCollide(){
        if(getRebirth1State() && getRebirth2State()){
            return true;
        }
        return false;
    }
    
    public void setRebirth1(Rebirth rebirth1){
        this.rebirth1 = rebirth1;
    }
    
    public void setRebirth2(Rebirth rebirth2){
        this.rebirth2 = rebirth2;
    }
    
    public Rebirth getRebirth1(){
        return rebirth1;
    }
    
    public Rebirth getRebirth2(){
        return rebirth2;
    }
    
    public boolean getRebirth1State(){
        return rebirth1.getRebirth();
    }
    
    public boolean getRebirth2State(){
        return rebirth2.getRebirth();
    }
    
}
