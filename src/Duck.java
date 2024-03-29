/* 
 @Authors: Mahlaki Henry and Jahlil Owens
 @ Date: 2/23/24
@ Title: Duck
 This is our original work
*/
public class Duck{ 
    private int name;
    private boolean flag = false; // 
    //private String step = "L"; // what step each duck is on (L for left and R for right)
    private int energy = 0; // energy for each duck
    private boolean hasCap = false; 
    private int position = 0;

    /*  READ ME: The number of ducks and number of positions are going to create a (position, # of ducks) grid. The ducks are labeled by what 
    square their in (e.g. 0 and 1 for the 1st EX on the HW). The flag will be on the same row as the duck that is allowed to 
    (the one with the cap). Use 'T' as a means to show that there was an energy swap followed by '->' symbolizing who received it. 
    This is what should be coded within the Duck and DuckState classes. Be sure to make toString methods! 
    And do the input too.
    
    */
    Duck(){
        
    }

    Duck(int name, boolean flag, int energy, boolean hasCap, int position){
        this.name = name;
        this.flag = flag;
        this.energy=  energy;
        this.hasCap = hasCap;
        this.position = position;
    }

    // Might get changed
    public void setName(int num) {
        this.name = num;
    }

    public int getName(){
        return name;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public int getEnergy(){
        return this.energy;
    }

    public void decreaseEnergy(){
        this.energy--;
    }

    public void increaseEnergy(){
        this.energy++;
    }

    public void setHasCap(){
        this.hasCap = true;
    }

    public boolean hasCap() {
        return hasCap;
    }

    public int getPosition(){
        return this.position;
    }

    public void setPosition(int pos){
        this.position = pos;
    }

    public void incPosition(){
        // Use L in this method
        this.position++;
    }

    public void decPosition(){
        // Use R in this method
        this.position--;
    }

    public void transferEnergy(Duck otDuck){
        if(this.energy > 0){ 
            this.energy--;
            otDuck.increaseEnergy(); 
        }

    }

    public void pickUpFlag(){
        this.flag = true;
    }

    public boolean hasFlag(){
        return this.flag;
    }

  
    public boolean compareTo(Duck o) {
       if (this.name == o.name && this.flag == o.flag && this.hasCap == o.hasCap 
       && this.position == o.position){
        return true;
       }
    
    return false;
    }
   
    
    
}//change
