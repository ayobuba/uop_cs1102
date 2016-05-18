package car;

public class Car {

    int maxSpeed = 100;
    int minSpeed = 0;
    double weight = 4079;
    boolean isTheCarOn = false;
    char condition = 'A';
    
    public void printVariables() {
        System.out.println("This is the max speed: " + maxSpeed);
        System.out.println("This is the min speed " + minSpeed);
        System.out.println("This is the weight: " + weight);
        System.out.println(isTheCarOn);
        System.out.println(condition);
    }
    
    public static void main(String[] args) {
        Car myCar = new Car();
        myCar.printVariables();
    }
}
