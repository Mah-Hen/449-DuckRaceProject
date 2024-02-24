import java.util.List;

public class DuckState{
    private Duck[] ducks; // ducks
    private int duckCounter; // How many ducks are there
    private int maxEnergy; // maxEnergy
    //private boolean flag; // has flag been picked up
    private int numofpositions = 0; // number of positions/ length of board
    private int duckWithCap;

    /*  READ ME: The number of ducks and number of positions are going to create a (position, # of ducks) grid. The ducks are labeled by what 
    square their in (e.g. 0 and 1 for the 1st EX on the HW). The flag will be on the same row as the duck that is allowed to 
    (the one with the cap). Use 'T' as a means to show that there was an energy swap followed by '->' symbolizing who received it. 
    This is what should be coded within the Duck and DuckState classes. Be sure to make toString methods!
    And do the input too.
    */

    public DuckState(int duckCounter, int numPositions, int duckWithCap, int maxEnergy) {
        this.duckCounter = duckCounter;
        this.maxEnergy = maxEnergy;
        this.numofpositions = numPositions-1; // The number is going to come in as 1 but we start at 0.
        this.duckWithCap = duckWithCap;
        ducks = new Duck[this.duckCounter];

        for (int i=0; i<duckCounter; i++){
            ducks[i] = new Duck();
            ducks[i].setName(i);
            ducks[i].setEnergy(this.maxEnergy);
            if (i==duckWithCap){
                ducks[i].setHasCap();
            }
        }
       
        
    }

    public int getDuckCounter(){
        return this.duckCounter;
    }

    public Duck[] getDucks(){
            return ducks;
        }

    public int getmaxEnergy(){
        return this.maxEnergy;
    }

    public int getNumofPos(){
        return this.numofpositions+1;
    }

    public int getDuckWithCap(){
        return this.duckWithCap;
    }

    public Duck getDuck(int index){
        return this.ducks[index];
    }

    @Override
    public boolean equals(Object state){
        int count = 0;
        DuckState oState = (DuckState) state;

        for(int i =0; i<this.duckCounter;i++){
            Duck duck = this.getDuck(i);
            Duck duck2 = oState.getDuck(i);
            if(duck.compareTo(duck2)){
                count++;
            }
        }
        if(count==this.duckCounter){
            return true;
        }
        return false;
    }

    
}
//change