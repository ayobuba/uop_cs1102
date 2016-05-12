/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inheritance;

/**
 *
 * @author brocd8s
 */
public class Inheritance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}

class Dog {
    int legs;
    char tail;
    String color, breed;
    
    Dog () {
        // ;
    }
    
    Dog (int l, char t, String c, String b) {
        legs = l;
        tail = t;
        color = c;
        breed = b;
    }
    void getBreed() {
        System.out.println("Breed is: " + breed);
    }
}

/**
 *
 * @author brocd8s
 */
public class myDog extends Dog {
    char hairlength;
     
    myDog () {
        hairlength = 's';
    }
    
    myDog (int l, char t, String c, String b, char hl) {
        super (l, t, c, b);
        hairlength = hl;
    }
    
    public static void main (String args[]) {
        MatchBox mb1 = new myDog (4, 'Y', 'white', 'beagle', 'S');
        mb1.getBreed();
    }
}