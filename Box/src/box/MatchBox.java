/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package box;

/**
 *
 * @author brocd8s
 */
public class MatchBox extends Box {
    
    w = 5;
    h = 10;
    d = 3;
    
    public void getVolume() {
        double v = 0;
        
        // returns the values of width, height, depth, and weight
        return w;
        return h;
        return d;
        // calculates and returns the volume by multiplying height by width by depth
        v = w * h * d;
    }
    
    public void calculateWeight() {
        //  calculates weight based on volume 
        // water weighs .03611 pounds per cubic inch
        System.out.println("weight of " + MatchBox + " is " + X);
    }
    
}
