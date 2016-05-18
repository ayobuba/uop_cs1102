package javatutorial;

/**
 *
 * @author brocd8s
 */
public class myClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        student mark = new student; // object or instance
        mark.setId(1);
        mark.setName("Mark");
        mark.setAge(36);

        student tom = new student; // object or instance
        mark.setId(2);
        mark.setName("Tom");
        mark.setAge(40);

        System.out.println(tom.getName() + " is " + tom.getAge());
        System.out.println(mark.getName() + " is " + mark.getAge());
    }
}
