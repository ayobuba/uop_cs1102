package snakeeyes;

public class Main {

    public static void main(String[] args) {

        int countSnakeEyes = 0;

        int die1 = 0;
        int die2 = 0;

        do {

            die1 = (int)(Math.random()*6)+1;

            die2 = (int)(Math.random()*6)+1;

               countSnakeEyes += 1;

               } while (die2 != die1);

        System.out.println("Snake Eyes after " + countSnakeEyes + " rolls of the dice");

    }
}