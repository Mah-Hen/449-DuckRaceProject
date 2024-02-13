public class DuckState{
    private Duck[] ducks; // ducks
    private int duckCounter; // How many ducks are there
    private int maxEnergy; // maxEnergy
    private boolean flag; // has flag been picked up
    private int numofpositions = 0; // number of positions

    /*  READ ME: The number of ducks and number of positions are going to create a (position, # of ducks) grid. The ducks are labeled by what 
    square their in (e.g. 0 and 1 for the 1st EX on the HW). The flag will be on the same row as the duck that is allowed to 
    (the one with the cap). Use 'T' as a means to show that there was an energy swap followed by '->' symbolizing who received it. 
    This is what should be coded within the Duck and DuckState classes. Be sure to make toString methods!
    
    */

    public DuckState(int duckCounter, int numPositions, int duckWithCap, int maxEnergy) {
        this.duckCounter = duckCounter;
        this.maxEnergy = maxEnergy;
        this.numofpositions = numPositions;

        for (int i=0; i<duckCounter; i++){
            ducks[i] = new Duck();
            ducks[i].setName(i);
            ducks[i].setEnergy(this.maxEnergy);
            if (i==duckWithCap){
                ducks[i].setHasCap();
            }
        }
       
        
    }
}
