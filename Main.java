package battleship;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        action();
    }

    public static void action() {
        String[][] ocean1 = new String[12][12]; // Initializing both playing fields
        String[][] hiddenOcean1 = new String[12][12];
        String[][] ocean2 = new String[12][12];
        String[][] hiddenOcean2 = new String[12][12];
        boolean didWin1 = false;
        boolean didWin2 = false;
        boolean player1Turn = true;
        createMap(ocean1);
        createMap(ocean2);
        createMap(hiddenOcean1);
        createMap(hiddenOcean2);

        System.out.println("Player 1, place your ships on the game field");
        placeShips(ocean1);

        System.out.println(); // prints playing field
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(ocean1[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println();

        promptEnterKey();



        System.out.println("Player 2, place your ships on the game field");
        placeShips(ocean2);

        System.out.println(); // prints playing field for first stage of the game
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(ocean2[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println();

        promptEnterKey();



        while (didWin1 !=  true && didWin2 != true) { // "main" program

            if (player1Turn) {
                System.out.println("Player 1, it's your turn:\n");
                // prints playing field for the second stage of the game
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 11; j++) {
                        System.out.print(hiddenOcean2[i][j]);
                    }
                    System.out.print("\n");
                }
                System.out.println("---------------------");
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 11; j++) {
                        System.out.print(ocean1[i][j]);
                    }
                    System.out.print("\n");
                }
                shootGuns(ocean2, hiddenOcean2);
                player1Turn = false; // Change between Player 1 and Player 2
            } else {
                System.out.println("Player 2, it's your turn:\n");
                // prints playing field for the second stage of the game
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 11; j++) {
                        System.out.print(hiddenOcean1[i][j]);
                    }
                    System.out.print("\n");
                }
                System.out.println("---------------------");
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 11; j++) {
                        System.out.print(ocean2[i][j]);
                    }
                    System.out.print("\n");
                }
                shootGuns(ocean1, hiddenOcean1);
                player1Turn = true; // Change between Player 1 and Player 2
            }


            int shipCounter = 0; // Checks win Condition Player 2; no more O on the field == win
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if("O ".equals(ocean1[i][j])) {
                        shipCounter++;
                    }
                    didWin2 = shipCounter == 0;
                }
            }

            shipCounter = 0;
            for (int i = 1; i < 11; i++) { // Checks win Condition Player 1; no more O on the field == win
                for (int j = 1; j < 11; j++) {
                    if("O ".equals(ocean2[i][j])) {
                        shipCounter++;
                    }
                    didWin1 = shipCounter == 0;
                }
            }

            if (didWin1 || didWin2) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            }
            promptEnterKey();

        }





    }

    public static void promptEnterKey() {

        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shootGuns(String[][] ocean, String[][] hiddenOcean) {
        boolean didShoot= false;
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        Scanner scanner = new Scanner(System.in);
        boolean shipEnd1 = false;
        boolean shipEnd2 = false;
        while(!didShoot) {
            System.out.println("Take a shot!\n");
            String[] cord1 = scanner.next().split("(?=\\d)(?<!\\d)");
            String x1 = cord1[0];
            int y1 = Integer.parseInt(cord1[1]);
            int row1 = 0;
            for (int j = 0; j < letters.length; j++) { // Getting row from letter
                if (letters[j].equals(x1)) {
                    row1 = j + 1;
                }
            }
            if (row1 == 0 || y1 < 1 || y1 > 10) { // Checks for input mistakes
                System.out.println("Error! Wrong Input. Try again:\n");
            } else {
                if ("O ".equals(ocean[row1][y1])) {
                    ocean[row1][y1] = "X ";
                    hiddenOcean[row1][y1] = "X ";
                    System.out.println("You hit a ship!");
                    didShoot = true;
                    for (int i = 1, j = 1; i <= 5 || j <= 5; i++, j++) { // checks if hit has sunk a ship
                        if ("X ".equals(ocean[row1][y1 + 1]) || "X ".equals(ocean[row1][y1 - 1])) {// is the ship horizontal
                            if("O ".equals(ocean[row1][y1 + i]) || "O ".equals(ocean[row1][y1 - j])) {
                                break;
                            }
                            if (!"X ".equals(ocean[row1][y1 + i])) {
                                i = 0;
                                shipEnd1 = true;
                            }
                            if (!"X ".equals(ocean[row1][y1 - j])) {
                                j = 0;
                                shipEnd2 = true;
                            }
                        }

                        if("X ".equals(ocean[row1 + 1][y1]) || "X ".equals(ocean[row1 - 1][y1])) {// is the ship vertical
                            if("O ".equals(ocean[row1 + i][y1]) || "O ".equals(ocean[row1 - j][y1])) {
                                break;
                            }
                            if (!"X ".equals(ocean[row1 + i][y1])) {
                                i = 0;
                                shipEnd1 = true;
                            }
                            if (!"X ".equals(ocean[row1 - j][y1])) {
                                j = 0;
                                shipEnd2 = true;
                            }
                        }

                        if (shipEnd1 && shipEnd2) {
                            System.out.println("You sank a ship!");
                            break;
                        }

                    }
                } else if ("X ".equals(ocean[row1][y1])) {
                    System.out.println("You already hit that!");
                    didShoot = true;
                } else {
                    ocean[row1][y1] = "M ";
                    hiddenOcean[row1][y1] = "M ";
                    System.out.println("You missed!");
                    didShoot = true;
                }
            }



        }
    }


    public static void createMap(String[][] ocean) {
        ocean[0][0] = "  ";
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 1; i < 11; i++) {
            ocean[i][0] = letters[i - 1] + " ";
            for (int j = 1; j < 11; j++) {
                ocean[0][j] = j + " ";
                ocean[i][j] = "~ ";

            }
        }
    }

    public static void placeShips(String[][] ocean) {
        Scanner scanner = new Scanner(System.in);
        int size = 0;
        int[] cells = {5, 4, 3, 3, 2};
        boolean inputGood = true;

        String[] ships = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        for (int k = 0; k < 5; k++) { // controls amount of placed ships max 5; min 1;

            if (inputGood) {

                System.out.println(); // prints playing field
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 11; j++) {
                        System.out.print(ocean[i][j]);
                    }
                    System.out.print("\n");
                }
                System.out.println();

                System.out.printf("Enter the coordinates of the %s (%d cells): \n\n", ships[k], cells[k]);
            }

            size = cells[k];

            String[] cord1 = new String[1];
            String[] cord2 = new String[1];

            cord1 = scanner.next().split("(?=\\d)(?<!\\d)");
            cord2 = scanner.next().split("(?=\\d)(?<!\\d)"); // separates letters from numbers
            String x1 = cord1[0];
            String x2 = cord2[0];
            int y1 = Integer.parseInt(cord1[1]);
            int y2 = Integer.parseInt(cord2[1]);
            int checkSum = 0;
            boolean allowPlacement = false;
            inputGood = true;
            if (!x1.equals(x2) && y1 != y2) { // Checks correct Placement
                System.out.println("Error! Wrong ship location! Try Again:\n");
                k--;
                inputGood = false;
            } else if (Math.abs(y1 - y2)  + 1 != size && Math.abs(x1.charAt(0) - x2.charAt(0)) + 1 != size) { // size Check
                System.out.printf("Error! Wrong length of the %s! Try Again:\n\n", ships[k]);
                k--;
                inputGood = false;
            }

            if (inputGood) {
                int row1 = 0;
                int row2 = 0;
                for (int j = 0; j < letters.length; j++) { // Getting row from letter
                    if (letters[j].equals(x1)) {
                        row1 = j + 1;
                    }
                    if (letters[j].equals(x2)) {
                        row2 = j + 1;
                    }
                }
                // Actual Placing of the Ships
                if (x1.equals(x2)) { // same column
                    if (y1 - y2 > 0) { // y1 > y2
                        for (int i = 0; i < size; i++) {
                            if ("O ".equals(ocean[row1][y2 + i]) || "O ".equals(ocean[row1 + 1][y2 + i]) ||
                                    "O ".equals(ocean[row1 - 1][y2 + i]) || "O ".equals(ocean[row1][y2 + i + 1]) ||
                                    "O ".equals(ocean[row1][y2 + i - 1])) { // checks if ships are to close
                                System.out.println("Error!: You cant place the ship here! Try Again: \n");
                                inputGood = false;
                                k--;
                                break;
                            } else if(!allowPlacement){
                                checkSum++;
                            }
                            if (checkSum == size) {
                                i = 0;
                                checkSum = size + 1;
                                allowPlacement = true;
                            }
                            if (allowPlacement) {
                                ocean[row1][y2 + i] = "0 ";
                            }

                        }
                    } else { // y2 > y1
                        for (int i = 0; i < size; i++) {
                            if ("O ".equals(ocean[row1][y1 + i]) || "O ".equals(ocean[row1 + 1][y1 + i]) ||
                                    "O ".equals(ocean[row1 - 1][y1 + i]) || "O ".equals(ocean[row1][y1 + i + 1]) ||
                                    "O ".equals(ocean[row1][y1 + i - 1])) { // checks if ships are to close
                                System.out.println("Error!: You cant place the ship here! Try Again: \n");
                                inputGood = false;
                                k--;
                                break;
                            } else if(!allowPlacement){
                                checkSum++;
                            }
                            if (checkSum == size) {
                                i = 0;
                                checkSum = size + 1;
                                allowPlacement = true;
                            }
                            if (allowPlacement) {
                                ocean[row1][y1 + i] = "0 ";
                            }

                        }
                    }
                } else { // same row
                    if (row1 > row2) {
                        for (int i = 0; i < size; i++) {
                            if ("O ".equals(ocean[row2 + i][y2]) || "O ".equals(ocean[row2 + i + 1][y2]) ||
                                    "O ".equals(ocean[row2 + i - 1][y2]) || "O ".equals(ocean[row2 + i][y2 + 1]) ||
                                    "O ".equals(ocean[row2 + i][y2 - 1])) { // checks if ships are to close
                                System.out.println("Error!: You cant place the ship here! Try Again: \n");
                                inputGood = false;
                                k--;
                                break;
                            } else if(!allowPlacement){
                                checkSum++;
                            }
                            if (checkSum == size) {
                                i = 0;
                                checkSum = size + 1;
                                allowPlacement = true;
                            }
                            if (allowPlacement) {
                                ocean[row2 + i][y2] = "0 ";
                            }

                        }
                    } else { // row2 > row1
                        for (int i = 0; i < size; i++) {
                            if ("O ".equals(ocean[row1 + i][y2]) || "O ".equals(ocean[row1 + i + 1][y2]) ||
                                    "O ".equals(ocean[row1 + i - 1][y2]) || "O ".equals(ocean[row1 + i][y2 + 1]) ||
                                    "O ".equals(ocean[row1 + i][y2 - 1])) { // checks if ships are to close
                                System.out.println("Error!: You cant place the ship here! Try Again: \n");
                                inputGood = false;
                                k--;
                                break;
                            } else if(!allowPlacement){
                                checkSum++;
                            }
                            if (checkSum == size) {
                                i = 0;
                                checkSum = size + 1;
                                allowPlacement = true;
                            }
                            if (allowPlacement) {
                                ocean[row1 + i][y2] = "0 ";;
                            }

                        }
                    }
                }
                // Workaround replaces 0s with Os, without it the program detects the current ship
                for (int i = 1; i < 11; i++) {
                    for (int j = 1; j < 11; j++) {
                        if("0 ".equals(ocean[i][j])) {
                            ocean[i][j] = "O ";
                        }
                    }
                }
            }
        }
    }
}
