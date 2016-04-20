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
            while (countSnakeEyes <= 1000) {
                die1 = (int)(Math.random()*6)+1;
                die2 = (int)(Math.random()*6)+1;
                totalRolls += 1;
                if (die1 == 1 && die2 == 1) {
                    countSnakeEyes += 1;
                    avgSErolls = countSnakeEyes / totalRolls;
                }
            }
        TextIO.putln(avgSErolls + " rolls to get Snake Eyes");
                

}}
        
        
        

    
