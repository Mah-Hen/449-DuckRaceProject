import java.util.Scanner;

public class DuckState {
    private String Duck;
    private int Duckcounter;
    private int energy;
    private boolean flag;

    public DuckState(String duck, int Duckcounter,int energy) {
        this.Duck = duck;
        this.Duckcounter = 0;
        this.energy = 0;
    }

    public void duckchecker() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter how many Ducks you want to create:");
            int number = scanner.nextInt();
            Duckcounter += number;
        }

    }



}

