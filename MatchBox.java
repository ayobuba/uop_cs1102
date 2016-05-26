

class Box{
    double width;
    double height;
    double depth;

    Box(){
    }

    Box(double w, double h, double d){
            width = w;
            height = h;
            depth = d;
    }
    
    void getVolume(){
            System.out.println("Volume is : "+width*height*depth);
    }
}

public class MatchBox extends Box{
    double weight;

    MatchBox() {
    }
    
    MatchBox(double w, double h, double d, double m) {

            super(w,h,d);
            weight = m;
    }

    public static void main(String args[])
    {
        MatchBox mb1 = new MatchBox(10,10,10,10);

        mb1.getVolume();
        System.out.println("width of MatchBox 1 is " + mb1.width);
        System.out.println("height of MatchBox 1 is " + mb1.height);
        System.out.println("depth of MatchBox 1 is " + mb1.depth);
        System.out.println("weight of MatchBox 1 is " + mb1.weight);


    }
}

