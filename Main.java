import java.io.*;
import static java.lang.Math.rint;

/**
 * The main class that runs the Pokemon Mind Reader Game.
 *
 * @author Claude Daniel 2017
 */
public class Main {

    /**
     * This is where the main game takes place. First the player is given the choice to
     * choose Veteran or Beginner if a file exists. This is where the comparison between the
     * different elements is done to determine the winner.
     * @param args
     */
    public static void main(String[] args) {
        /* The score for the robot. Starts at 0. */
        int robotScore = 0;

        /* The score for the player. Starts at 0. */
        int playerScore = 0;

        /* The file path for a potential saved Computer object. */
        File f = new File( "computer.dat" );
        Computer robot = new Computer();

        /* Assumes the default choice is 'Beginner' is no saved file is found. */
        int continueGameChoice = 2;

        /*This is the process for checking if a save file exists and giving the user the option
        to choose whether to load it.*/
        if( f.exists() ) {
            System.out.println("An existing computer opponent was found.\n" +
                    "Choose 'Veteran' to load this opponent or 'Beginner' to start from scratch.\n1. Veteran\n2. Beginner");
            continueGameChoice = CheckInput.checkIntRange(1, 2);
            if (continueGameChoice == 1) {
                try {
                    ObjectInputStream inSaveData = new ObjectInputStream(new FileInputStream(f));
                    robot = (Computer) inSaveData.readObject();
                    inSaveData.close();
                } catch (IOException e) {
                    System.out.println("Error processing file.");
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Could not find class.");
                }
            }
        }

        /*Here, if the choice was 'Beginner', a new String "xxxx" is created to
        start off with. If 'Veteran' was chosen, the last pattern stored in
        the saved Computer file is loaded.*/
        String patternString = "";
        if (continueGameChoice == 2) {
            int patternLength = 4;
            for (int i = 0; i < patternLength; i++) {
                patternString = patternString + 'x';
            }
        } else {
            patternString = robot.getSavedPattern().getPatternString();
        }
        /*This is the main recurring menu of the game. The user can choose an element to play or quit the game.
        If user quits, the option to save the state of the computer is presented to the user.*/
        int choice = 0;
        while (choice != 4){
            char robotChoice = robot.predictPattern(patternString);
            System.out.println("What would you like to do?\n1. Play Fire\n2. Play Water\n3. Play Grass\n4. EXIT GAME ");
            choice = CheckInput.checkIntRange(1, 4);
            char[] miniTypeArray = {'f', 'w', 'g'};
            String[] typeArray = {"Fire", "Water", "Grass"};
            if (choice == 4) {
                System.out.println("Would you like to save the state of the opponent?\n1. Yes\n2. No");
                int saveChoice = CheckInput.checkIntRange(1, 2);
                if (saveChoice == 1){
                    try{
                        File outFile = new File( "computer.dat" );
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile));
                        out.writeObject(robot);
                        out.close();
                    }catch( IOException e ) {
                        System.out.println("Error processing file.");
                    }
                }
                System.out.println("Quitting...");
                break;
            }
            char typeChoice = miniTypeArray[choice - 1];
            System.out.println("You chose "+ typeArray[choice-1] + "!");
            patternString = patternString + typeChoice;
            patternString = patternString.substring(1);
            System.out.println("The pattern including current choice: " + patternString);
            robot.storePattern(patternString);

            /*Here is the logic to determine which of the three elements wins.
            The player and robot scores are increased accordingly.*/
            if (robotChoice == 'f') {
                System.out.println("The computer chose Fire!");
            } else if (robotChoice == 'w'){
                System.out.println("The computer chose Water!");
            } else {
                System.out.println("The computer chose Grass!");
            }
            if ((choice == 1 && robotChoice == 'f') || (choice == 2) && (robotChoice == 'w') || (choice == 3 && robotChoice == 'g')){
                System.out.println("Its a draw!");
                System.out.println("Your score: "+ playerScore);
                System.out.println("Opponent's score: "+ robotScore);
                System.out.println("The computer has won "+ rint((robotScore/(double)(playerScore+robotScore))*100) + "% of rounds so far.");
            } else if ((choice == 1 && robotChoice == 'g') || (choice == 2) && (robotChoice == 'f') || (choice == 3 && robotChoice == 'w')){
                System.out.println("You win!");
                System.out.println("Your score: "+ ++playerScore);
                System.out.println("Opponent's score: "+ robotScore);
                System.out.println("The computer has won "+ rint((robotScore/(double)(playerScore+robotScore))*100) + "% of rounds so far.");
            } else {
                System.out.println("You lose!");
                System.out.println("Your score: "+ playerScore);
                System.out.println("Opponent's score: "+ ++robotScore);
                System.out.println("The computer has won "+ rint((robotScore/(double)(playerScore+robotScore))*100) + "% of rounds so far.");
            }
        }
    }
}
