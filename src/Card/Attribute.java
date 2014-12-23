/**
 * Trigger.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */

package Card;

import java.util.ArrayList;

/**
 * Trigger class.
 */
public class Attribute {
    /**
     * Enum types for attribute.
     *
     */
    public enum Attributes { ALARM, ASSIST, BACKUP, COUNTER, ACCELERATE, BODYGUARD, BRAINSTORM, 
        CHANGE, ENCORE, RECOLLECTION, EXPERIENCE, BOND, NONE; 
                public String toString() {  
                    switch (this) {
                        case ALARM: return "Alarm";
                        case ASSIST: return "Assist";
                        case BACKUP: return "Backup";
                        case COUNTER: return "Counter";
                        case ACCELERATE: return "Accelerate";
                        case BODYGUARD: return "Bodyguard";
                        case BRAINSTORM: return "Brainstorm";
                        case CHANGE: return "Change";
                        case ENCORE: return "Encore";
                        case RECOLLECTION: return "Recollection";
                        case EXPERIENCE: return "Experience";
                        case BOND: return "Bond";
                        default: return "None";
                    }
                }
    }
    
    
    
    private ArrayList<Attributes> attributes;

    
    public Attribute(Attributes attribute) {
        attributes = new ArrayList<>(1);
        attributes.add(attribute);
    }
    
    public static int getLongestAttributeLength() {
        final int LONGEST_ATTRIBUTE_LENGTH = 21;
        return LONGEST_ATTRIBUTE_LENGTH;
    }
    
    public static String[] getListOfAttributes() {
        final String[] listOfAttributes = {"ALARM", "ASSIST", "BACKUP", "[Counter]", "ACCELERATE", "BODYGUARD", "BRAINSTORM", 
            "CHANGE", "ENCORE", "RECOLLECTION", "EXPERIENCE", "BOND" };
        return listOfAttributes;
    }
  
    
    /**
     * Constructor for Trigger object with an Array of triggers represented 
     * as Strings.
     * @param types String[] of triggers represented as strings
     */
    public Attribute(ArrayList<String> types) {
        attributes = new ArrayList<Attributes>(types.size());
        for (String attribute : types) {
            attributes.add(stringToType(attribute));
        }
            
    }
    
    /**
     * Converts string to Trigger type.
     * @param color Color
     */
    public Attributes stringToType(String attribute) {
        attribute = attribute.toLowerCase();
        Attributes type = null;
        if (attribute.equals("alarm"))
            type = Attributes.ALARM;
        else if (attribute.equals("assist"))
            type = Attributes.ASSIST;
        else if (attribute.equals("backup"))
            type = Attributes.BACKUP;
        else if (attribute.equals("[counter]"))
            type = Attributes.COUNTER;
        else if (attribute.equals("accelerate"))
            type = Attributes.ACCELERATE;
        else if (attribute.equals("bodyguard"))
            type = Attributes.BODYGUARD;
        else if (attribute.equals("brainstorm"))
            type = Attributes.BRAINSTORM;
        else if (attribute.equals("change"))
            type = Attributes.CHANGE;
        else if (attribute.equals("encore"))
            type = Attributes.ENCORE;
        else if (attribute.equals("recollection"))
            type = Attributes.RECOLLECTION;
        else if (attribute.equals("experience"))
            type = Attributes.EXPERIENCE;
        else if (attribute.equals("bond"))
            type = Attributes.BOND;
        else
            type = Attributes.NONE;
        return type;
    }
    
    /**
     * Setter for type field.
     * @param triggers Triggers
     */
    public void setAttribute(ArrayList<Attributes> attributes) {
        this.attributes = attributes;
    }
    
    /**
     * Getter for type field.
     * @return triggers
     */
    public ArrayList<Attributes> getTriggers() {
        return attributes;
    }

    /**
     * toString for Trigger class.
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < attributes.size(); i++) {
            str+= attributes.get(i);
            if (i + 1 == attributes.size())
                break;
            str+= ", ";
        }
        return str;
    }
}
