public class Duck { 
    private String name = "";
    private boolean flag = false; // 
    private String step = "L"; // what step each duck is on (L for left and R for right)
    private int energy = 0; // energy for each duck
    private boolean hasCap = false; 

    /*  READ ME: The number of ducks and number of positions are going to create a (position, # of ducks) grid. The ducks are labeled by what 
    square their in (e.g. 0 and 1 for the 1st EX on the HW). The flag will be on the same row as the duck that is allowed to 
    (the one with the cap). Use 'T' as a means to show that there was an energy swap followed by '->' symbolizing who received it. 
    This is what should be coded within the Duck and DuckState classes. Be sure to make toString methods! 
    And do the input too.
    
    */
    Duck(){
        
    }

    public void setName(int num) {
        this.name = this.step + this.name + num;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void setHasCap(){
        this.hasCap = true;
    }
    
}
