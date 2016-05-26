package box;

public class main {

    public static void main(String[] args) {

    // creates a new MatchBox object
    MatchBox mb = new MatchBox(5, 10, 3);

    // returns the width, height and depth
    System.out.println("width of MatchBox is " + mb.width);
    System.out.println("height of MatchBox is " + mb.height);
    System.out.println("depth of MatchBox is " + mb.depth);
    mb.calculateWeight(); // calls getWeight method
    mb.getVolume(); // calls getVolume method
    
}
}