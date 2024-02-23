public class Duck{ 
    private int name; // Number that represents each Duck
    private boolean flag = false; // If flag is picked up or not
    private int energy = 0; // Energy for each duck
    private boolean hasCap = false; // To identify the duck with the cap.
    private int position = 0;
    /* Constructors */
    Duck(){
        
    }
    
    Duck(int name, boolean flag, int energy, boolean hasCap, int position){
        this.name = name;
        this.flag = flag;
        this.energy=  energy;
        this.hasCap = hasCap;
        this.position = position;
    }
    
    // Set the name of the duck
    public void setName(int num) {
        this.name = num;
    }
    
    // Gets the ducks name
    public int getName(){
        return name;
    }
    
    // Set the energy for the ducks
    public void setEnergy(int energy){
        this.energy = energy;
    }
    
    // Gets the energy from the ducks
    public int getEnergy(){
        return this.energy;
    }
    
    //  Reduces the energy by a certain amount
    public void decreaseEnergy(){
        this.energy--;
    }
    
    // Increases the ducks energy
    public void increaseEnergy(){
        this.energy++;
    }
    
    // Sets the cap value
    public void setHasCap(){
        this.hasCap = true;
    }
    
    // Returns true or false if a duck has a cap
    public boolean hasCap() {
        return hasCap;
    }
    
    // Gets the ducks current position
    public int getPosition(){
        return this.position;
    }
    
    // Sets the ducks position
    public void setPosition(int pos){
        this.position = pos;
    }
    
    // Tells the duck to move forward
    public void incPosition(){
        this.position++;
    }
    
    // Tells the duck to move backwards
    public void decPosition(){
        this.position--;
    }
    
    // Allows two ducks to tranfer their energy
    public void transferEnergy(Duck otDuck){
        if(this.energy > 0){ 
            this.energy--;
            otDuck.increaseEnergy(); 
        }
    }
    
    // Allows the duck to pick up the flag
    public void pickUpFlag(){
        this.flag = true;
    }
    
    // Allows us to see if the duck has the flag
    public boolean hasFlag(){
        return this.flag;
    }
    
    // allows us to compare objects by their name, flag, cap, and position
    public boolean compareTo(Duck o) {
        if (this.name == o.name && this.flag == o.flag && this.hasCap == o.hasCap 
        && this.position == o.position){
            return true;
        }
        
        return false;
    }
}