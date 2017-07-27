import java.io.*;
/**
 * This class stores the string into a Pattern object.
 * Created by Claude Daniel on 3/6/17.
 */

public class Pattern implements Serializable{
    /* This is the string key that is stored within the pattern object. */
    private String key;

    /**
     * The constructor for the Pattern object.
     * @param patternString the string of chosen length that contains
     *                      past choices.
     */
    public Pattern(String patternString){
        key = patternString;
    }

    /**
     * Overrides the hashCode method.
     * @return the created unique hash code.
     */
    @Override
    public int hashCode(){
        int prime = 31;
        int result = 1;
        for (int i = 0; i<key.length(); i++){
            result = prime * result + key.charAt(i) * (i+1);
        }
        return result;
    }

    /**
     *  Overrides the equals method.
     * @param o a generic object. Its used to check whether this generic
     *          object is and instance of a Pattern.
     * @return a boolean of whether the the object is a Pattern of not.
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof Pattern){
            Pattern p = (Pattern) o;
            return key.equals(p.key);
        }
        return false;
    }

    /**
     * Returns the string stored within the pattern object.
     * @return the string or key.
     */
    public String getPatternString(){
        return key;
    }
}
