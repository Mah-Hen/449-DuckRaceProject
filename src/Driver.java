/* 
 @Authors: Mahlaki Henry and Jahlil Owens
 @ Date: 2/23/24
@ Title: Driver
 This is our original work
*/

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

        DuckState initial = new DuckState(4, 4, 1, 4);
        Node<DuckState> Project1 = new Node<>(initial, null);
        System.out.printf("Breadth-First Search:\n%s\n", Project1.breadthFirstSearch());
        System.out.printf("Best-First Search:\n%s\n", Project1.bestFirstSearch());
        System.out.printf("A* Search: \n%s", Project1.aStarSearch());
        

    }
} //change
