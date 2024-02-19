import java.util.ArrayList;
import java.util.List;

public class DuckState{
    private Duck[] ducks; // ducks
    private int duckCounter; // How many ducks are there
    private int maxEnergy; // maxEnergy
    private boolean flag; // has flag been picked up
    private int numofpositions = 0; // number of positions
    //private String step = "L"; // what step each duck is on (L for left and R for right)
    private int duckWithCap;


    /* update the get duck method to where we call the duck constructor from the duck class.
    */

    public DuckState(int duckCounter, int numPositions, int duckWithCap, int maxEnergy) {
        this.duckCounter = duckCounter;
        this.maxEnergy = maxEnergy;
        this.numofpositions = numPositions;
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

    public int getmaxEnergy() {
        return maxEnergy;
    }

    public int getduckwithcap() {
        return duckWithCap;
    }

    public List<DuckState> moveAction(int fromPosition, int toPosition){
        if (fromPosition < 0 || fromPosition >= numofpositions || 
        toPosition < 0 || toPosition >= numofpositions) {
            return null;
        }
        Duck movement = ducks[fromPosition];
        if(movement == null) {
            return null; 
        }


        if (!(toPosition == fromPosition - 1 || toPosition == fromPosition + 1)) {
            return null;  // Not adjacent positions
        }

        for (int i = 0; i < ducks.length; i++) {
            if (ducks[i].getPosition() == toPosition) {
                return null; // can't move a duck that is already in the target position
            }
        }

        if (!flag && fromPosition == duckWithCap) {
            Duck capduck = ducks[fromPosition];
            capduck.setflag(true);
            flag = true;
        }

        movement.setPosition(toPosition);
        movement.decreaseEnergy();

        //check to see if we need the duckwithcap variable for this class at top and below

        DuckState duckState = new DuckState(duckCounter, numofpositions, duckWithCap, maxEnergy);
        List<DuckState> result = new ArrayList<>();
        result.add(duckState);
        return result;
    }


    public List<DuckState> energySwapAction(int duck1Index, int duck2Index){
        return null;
    }

    public int getNumofPos(){
        return this.numofpositions;
    }


    /** 
    public void move() {
        if (step.equals("L")) {
            step = "R";
        }
        else {
            step = "L";
        }
        setName(Integer.parseInt(name.substring(1)));
    }**/
}