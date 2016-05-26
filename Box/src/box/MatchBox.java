package box;

public class MatchBox extends Box {
    
    double weight; // declare and initialize weight variable
       
    public void calculateWeight() {
        // calculates weight based on volume 
        // assuming density = water (.03611 pounds per cubic inch)
        weight = 0.03611 * (width * height * depth);
        System.out.println("weight of MatchBox is " + weight);
    }
    
    MatchBox(double w, double h, double d) {
        super(w, h, d);
    }
}
