/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
/*
    原本方式是仰賴最外面的whileLoop去跑++，
    一旦用了Timer，變成兩顆執行緒，
    那他的++和外面Timer就無關，變成平行的。

    現在要將它變成非平行的，跟著whileLoop走，
    所以需要不是開執行緒方式處理，現在要模擬類似Timer的做法。
*/
/**
 *
 * @author winniewang
 */
public class DelayCounter {
    private int delay;
    private int count;
    
    public DelayCounter(int delay){
        this.delay = delay * Global.ANIMA_DELAY;
        count = 0;
    }
    
    public boolean update(){
        if(count++ < delay){
            return false;
        }
        count = 0;
        return true;
    }
}
