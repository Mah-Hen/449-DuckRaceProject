import java.util.Scanner;

public class DuckState {
    private Duck[] ducks; // ducks
    private int duckCounter; // How many ducks are there
    private int maxEnergy; // maxEnergy
    private boolean flag; // has flag been picked up
    private int numofpositions = 0; // number of positions

    /*  READ ME: The number of ducks and number of positions are going to create a (position, # of ducks) grid. The ducks are labeled by what 
    square their in (e.g. 0 and 1 for the 1st EX on the HW). The flag will be on the same row as the duck that is allowed to 
    (the one with the cap). Use 'T' as a means to show that there was an energy swap followed by '->' symbolizing who received it. 
    This is what should be coded within the Duck and DuckState classes. Be sure to make toString methods!
    And do the input too.
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

    //still have to adjust the flag position aligned with the duck with cap

    public void duckwithflag() {
        for (int i = 0; i < ducks.length; i++) {
            ducks[i].move();
            if (ducks[i].hasCap()) {
                //String duckposition = duck.getName();
                //System.out.println(duckposition + " F ");
                System.out.println(ducks[i] + " ");
            }
        }
    }

    public void tracksteps() {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 0; i < ducks.length; i++) {
                ducks[i].move();
            }

            System.out.println("which duck is giving energy: ");
            int duckgiver = scanner.nextInt();
            System.out.println("which duck is receiving energy: ");
            int duckreciever = scanner.nextInt();

            if (duckgiver >= 0 && duckgiver < duckCounter && duckreciever >= 0 && duckreciever >= 0 && duckreciever < duckCounter && 
            duckgiver != duckreciever && ducks[duckgiver].getenergy() > 0) {
                ducks[duckgiver].transferEnergy(ducks[duckreciever]);
                System.out.println("T" + duckgiver);
            }
            else {
                System.out.println("Invalid energy transfer. Please try again.");
            }
        }
        for(int i = 0; i < ducks.length; i++) {
            if (ducks[i].hasCap() && ducks[i].getName().equals("L0")) {
                ducks[i].setflag(true);
                System.out.println("Duck " + ducks[i].getName() + " picked up the flag");
            }

            if (ducks[i].hasFlag() && !ducks[i].hasCap() && ducks[i].getName().equals(ducks[i].getName().substring(0, 1) + (duckCounter - 1))) {
                System.out.println("Duck " + ducks[i].getName() + " delivered the flag");
            }
            
        }
    }


}

