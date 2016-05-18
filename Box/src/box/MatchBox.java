package box;

public class MatchBox extends Box {
    
    private double weight;
    
    MatchBox() {
        
    }
    
    MatchBox(double w, double h, double d, double m) {
        super(w, h, d);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;        
    }
    
    public void calculateWeight() {
        // calculates weight based on volume 
        // water weighs .03611 pounds per cubic inch
        weight = 0.03611 * getVolume();
        System.out.println("weight of " + MatchBox + " is " + X);
    }
    
}
