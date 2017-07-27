import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

/**
 * The main A.I of the game. This Computer object is used to make predictions
 * based on user inputs by comparing to available past inputs.
 * Created by Claude Daniel on 3/6/17.
 */
public class Computer implements Serializable{
    /* This hashmap is used to store all past user inputs as patterns of a chosen length. */
    private HashMap<Pattern, Integer> patternMap = new HashMap<Pattern, Integer>();

    /* The sole purpose of this savedPattern is to have a starting pattern when
    the user chooses to continue the game from an existing computer opponent. */
    private Pattern savedPattern;

    /**
     * An empty constructor. No value is passed.
     */
    public Computer(){}

    /**
     * This method stores a pattern into the hashMap.
     * @param s the string to be stored.
     */
    public void storePattern(String s){
        savedPattern = new Pattern (s);
        Pattern tempPattern = new Pattern(s);
        if (patternMap.containsKey(tempPattern)){
            patternMap.replace(tempPattern, patternMap.get(tempPattern)+1);
        } else {
            patternMap.put(tempPattern, 1);
        }
    }

    /**
     * This method returns the savedPattern in case the user decides
     * to continue a saved game.
     * @return the saved pattern from the previous game.
     */
    public Pattern getSavedPattern(){
        return savedPattern;
    }

    /**
     * This is the main prediction algorithm of the computer. This method predicts the most
     * likely choice that the player is going to make next.
     * @param s the pattern containing the last choice.
     * @return the prediction as 'f', 'w', or 'g'.
     */
    public char predictPattern(String s){
        Random rand = new Random();
        char[] miniTypeArray = {'f', 'w', 'g'};
        String potentialChoiceF = s.substring(1) + 'f';
        String potentialChoiceW = s.substring(1) + 'w';
        String potentialChoiceG = s.substring(1) + 'g';

        /* An Integer is used instead of a primitive int since the function .get
        for the hashMap returns an Integer and not an int. Using an int may
        cause errors if a null is returned. */
        Integer instancesOfFire;
        Integer instancesOfWater;
        Integer instancesOfGrass;
        instancesOfFire = patternMap.get(new Pattern(potentialChoiceF));
        instancesOfWater = patternMap.get(new Pattern(potentialChoiceW));
        instancesOfGrass = patternMap.get(new Pattern(potentialChoiceG));
        if (instancesOfFire == null){
            instancesOfFire = 0;
        }
        if (instancesOfGrass == null){
            instancesOfGrass = 0;
        }
        if (instancesOfWater == null){
            instancesOfWater = 0;
        }

        /* This series of if else statements checks to see which potential choice is the most
        likely. If two user choices are equally likely, the computer makes a random choice between the
        options that may beat one of the two potential user choices. If no reasonable prediction can
        be made, an element is chosen at random. */
        if ((instancesOfFire == instancesOfWater) && (instancesOfFire > instancesOfGrass)){
            return miniTypeArray[rand.nextInt(2)+1];
        } else if ((instancesOfFire == instancesOfGrass) && (instancesOfFire > instancesOfWater)){
            return miniTypeArray[rand.nextInt(2)];
        } else if ((instancesOfWater == instancesOfGrass) && (instancesOfGrass>instancesOfFire)){
            if (rand.nextInt(2) == 0) return 'f';
            else return 'g';
        } else if ((instancesOfFire > instancesOfGrass) && (instancesOfFire > instancesOfWater)) {
            return 'w';
        } else if ((instancesOfWater > instancesOfFire) && (instancesOfWater > instancesOfGrass)) {
            return 'g';
        }
        else if ((instancesOfGrass > instancesOfFire) && (instancesOfGrass > instancesOfWater)) {
            return 'f';
        }
        else return miniTypeArray[rand.nextInt(3)];
    }
}
