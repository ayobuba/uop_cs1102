package box;

public static void main {
    public static void main(String[] args) {

    // create a variable for volume
    double volume;

    // creates a new MatchBox object
    MatchBox mBox = new MatchBox(5, 10, 3);

    // calls getVolume method
    volume = getVolume(mBox);

    // returns the results by printing
    System.out.println("width of MatchBox is " + width);
    System.out.println("height of MatchBox is " + height);
    System.out.println("depth of MatchBox is " + depth);
    System.out.println("weight of MatchBox is " + weight);
    System.out.println("Volume is: " + getVolume(mBox));
}
}

public static void main(String args[]) {
		MatchBox mb1 = new MatchBox(10, 10, 10, 10);
		mb1.getVolume();
		System.out.println("width of MatchBox 1 is " + mb1.width);
		System.out.println("height of MatchBox 1 is " + mb1.height);
		System.out.println("depth of MatchBox 1 is " + mb1.depth);
		System.out.println("weight of MatchBox 1 is " + mb1.weight);
	}