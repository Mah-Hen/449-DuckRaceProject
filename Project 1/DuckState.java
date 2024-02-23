import java.util.List;

public class DuckState{
    private Duck[] ducks; // ducks
    private int duckCounter; // How many ducks are there
    private int maxEnergy; // maxEnergy
    private int numofpositions = 0; // number of positions/ length of board
    private int duckWithCap; // Duck that has a cap 
    
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
    
    // Gets the number of ducks
    public int getDuckCounter(){
        return this.duckCounter;
    }
    
    // Gets the list of ducks being used
    public Duck[] getDucks(){
        return ducks;
    }
    
    // Gets the max energy a duck can have
    public int getmaxEnergy(){
        return this.maxEnergy;
    }
    
    // Gets the length of the board
    public int getNumofPos(){
        return this.numofpositions+1;
    }
    
    // Gets the value that aligns with the duck with cap
    public int getDuckWithCap(){
        return this.duckWithCap;
    }
    
    // Gets the index of all Ducks
    public Duck getDuck(int index){
        return this.ducks[index];
    }
    
    // Allows to see if two ducks equal to each other
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