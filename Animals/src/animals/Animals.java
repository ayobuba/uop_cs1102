public class Animals {
    private String name = "Animal";
    public String favFood = "Food";
    
    protected final void changeName(String newName);
        this.name = newName;
}
    protected final String newName();
        return this.name;
}
    public void eatStuff(){
        System.out.println("Yum " + favFood);
}

    public void walkAround() {
        System.out.println(this.name + " walks around");
}

    public Animals(){

}

    public Animals(String name, String favFood) {
        this.changeName(name);
        this.favFood = favFood;
}

}