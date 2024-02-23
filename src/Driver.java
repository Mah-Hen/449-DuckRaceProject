import java.util.Scanner;

public class Driver {

    public static String duckinput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input number of ducks, positions, which duck has a cap, the max energy between ducks: ");
        String input = scanner.nextLine();
       while (true) {
        if (input.length() > 4) {
            System.out.println( "Invalid Input! Please follow the format: DuckNumber, positions, duckwithcap and max energy: ");
            input = scanner.nextLine();
        }
        else {
            break;
        }
        }
        scanner.close();
        return input;
        
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
       /*String input = duckinput();
        String[] seperate = input.split("");
        int duckCounter = Integer.parseInt(seperate[0]);
        int numofpositions = Integer.parseInt(seperate[1]);
        int duckwithcap = Integer.parseInt(seperate[2]);
        int maxEnergy = Integer.parseInt(seperate[3]);
        */

        DuckState initial = new DuckState(7, 4, 3, 5);

        Node Project1 = new Node(initial, null);
        System.out.println(Project1.bestFirstSearch());
        

    }
}
