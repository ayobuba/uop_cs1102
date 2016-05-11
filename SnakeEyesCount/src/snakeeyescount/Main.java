/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeeyescount;

/**
 *
 * @author strongdan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int countSnakeEyes = 0;
        int die1 = 0;
        int die2 = 0;
        do {
            die1 = (int)(Math.random()*6)+1;
                //System.out.println(die1 + " rolling die1");
                die2 = (int)(Math.random()*6)+1;
                //System.out.println(die2 + " rolling die2");
                countSnakeEyes += 1;
                //System.out.println(countSnakeEyes + " count of snake eyes");
        }    
        while (die2 != die1);
        TextIO.putln("Snake Eyes after " + countSnakeEyes + " rolls of the dice");
            
    }
}
        
        
        
   
    
