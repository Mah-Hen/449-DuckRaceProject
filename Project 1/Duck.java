public class Duck { 
    private String name = "";
    private boolean flag = false; // 
    private String step = "L"; // what step each duck is on (L for left and R for right)
    private int energy = 0; // energy for each duck
    private boolean hasCap = false; 
    //private int ducknumber;

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

    public String getName() {
        return name;
    }
    public boolean hasFlag() {
        return flag;
    }
    public void setflag(boolean flag) {
        this.flag = flag;
    }

    public int getenergy() {
        return energy;
    }
    
    public boolean hasCap() {
        return hasCap;
    }
    
    public void transferEnergy(Duck otDuck) {
        if (this.energy > 0) {
            this.energy--;
            otDuck.setEnergy(otDuck.getenergy()+1);
            // other option otDuck.energy++;
        }
    }

    public void move() {
        if (step.equals("L")) {
            step = "R";
        }
        else {
            step = "L";
        }
        setName(Integer.parseInt(name.substring(1)));
    }

    public String toString() {
        String flagchecker = "";
        if(flag) {
            flagchecker = "Has a flag.";
        }
        return getName() + flagchecker;
    }


}
