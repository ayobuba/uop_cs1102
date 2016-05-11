package snakeeyes1000;

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
        int totalRolls = 0;
        int avgSErolls = 0;
        int die1 = 0;
        int die2 = 0;
        do {
            die1 = (int)(Math.random()*6)+1;
                //System.out.println(die1 + " rolling die1");
            die2 = (int)(Math.random()*6)+1;
                //System.out.println(die2 + " rolling die2");
            totalRolls += 1;
            if (die1 == die2) {
                countSnakeEyes += 1;
            }
                //System.out.println(countSnakeEyes + " count of snake eyes");
        }        
        while (countSnakeEyes <= 1000);
        avgSErolls = countSnakeEyes / totalRolls;
        TextIO.putln(avgSErolls + " average snake eyes per 1000 rolls");
                }
            }
        
                

}}
        
        
        

    
