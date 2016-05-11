public class icecreamcones {

  public static void main(String[] args) {

     Variable declaration
    String flavor;
    double numberOfIcecreams=0;
    double numberOfStrawberry=0;

     Read the icecream.dat file
    TextIO.readFile(icecream.dat);

     Running while End of File is false
    while ( TextIO.eof() == false ) {
       Count Total number of icecreams
      numberOfIcecreams++;
       Get next icecream flavor from the file
      flavor=TextIO.getlnWord();
       Check if icecream flavor is Strawberry
      if (flavor.equals(Strawberry))
       Count Total number of Strawberry icecreams
      numberOfStrawberry++;
      }
     Print results
    TextIO.putln(The number of icecream cones are  +numberOfIcecreams);
    TextIO.putln(The number of Strawberry icecream cones are +numberOfStrawberry);
    TextIO.putln(The percentage of Strawberry icecream cones is +numberOfStrawberrynumberOfIcecreams100);
    }
}  
