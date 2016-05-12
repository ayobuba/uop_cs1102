package objectexample;

class ObjectExample {
    public static void main(String[] args) {
        
        Dog myDog = new Dog(); // new dog object
        
        Dog yourDog = new Dog();
        myDog.size = 50;
        yourDog.size = 20;
        myDog.bark();
        yourDog.bark();
        
    }
}
