/**
 * Trigger.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */

package Card;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Trigger class.
 */
public class Ability implements Serializable {
    /**
     * Enum types for attribute.
     *
     */
    public enum Abilities { ALARM, ASSIST, BACKUP, COUNTER, ACCELERATE, BODYGUARD, BRAINSTORM, 
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
                
                public static Abilities[] getAbilitiesArray() {
                	return values();
                }
    }
    
    
    
    private ArrayList<Abilities> abilities;

    
    public Ability(Abilities ability) {
        abilities = new ArrayList<>(1);
        abilities.add(ability);
    }
    
    public Ability() {
    	abilities = new ArrayList<Abilities>();
    }
    
    public void addAbility(Abilities ability) {
    	abilities.add(ability);
    }
    
    public static int getLongestAbilityLength() {
        final int LONGEST_ATTRIBUTE_LENGTH = 21;
        return LONGEST_ATTRIBUTE_LENGTH;
    }
    
    public static String[] getListOfAbilities() {
        final String[] listOfAbilities = {"ALARM", "ASSIST", "BACKUP", "[Counter]", "ACCELERATE", "BODYGUARD", "BRAINSTORM", 
            "CHANGE", "ENCORE", "RECOLLECTION", "EXPERIENCE", "BOND" };
        return listOfAbilities;
    }
  
    
    /**
     * Constructor for Trigger object with an Array of triggers represented 
     * as Strings.
     * @param types String[] of triggers represented as strings
     */
    public Ability(ArrayList<String> types) {
        abilities = new ArrayList<Abilities>(types.size());
        for (String ability : types) {
            abilities.add(stringToType(ability));
        }
            
    }
    
    /**
     * Converts string to Trigger type.
     * @param color Color
     */
    public static Abilities stringToType(String ability) {
        ability = ability.toLowerCase();
        Abilities type = null;
        if (ability.equals("alarm"))
            type = Abilities.ALARM;
        else if (ability.equals("assist"))
            type = Abilities.ASSIST;
        else if (ability.equals("backup"))
            type = Abilities.BACKUP;
        else if (ability.equals("[counter]"))
            type = Abilities.COUNTER;
        else if (ability.equals("accelerate"))
            type = Abilities.ACCELERATE;
        else if (ability.equals("bodyguard"))
            type = Abilities.BODYGUARD;
        else if (ability.equals("brainstorm"))
            type = Abilities.BRAINSTORM;
        else if (ability.equals("change"))
            type = Abilities.CHANGE;
        else if (ability.equals("encore"))
            type = Abilities.ENCORE;
        else if (ability.equals("recollection"))
            type = Abilities.RECOLLECTION;
        else if (ability.equals("experience"))
            type = Abilities.EXPERIENCE;
        else if (ability.equals("bond"))
            type = Abilities.BOND;
        else
            type = Abilities.NONE;
        return type;
    }
    
    /**
     * Setter for type field.
     * @param triggers Triggers
     */
    public void setAbility(ArrayList<Abilities> abilities) {
        this.abilities = abilities;
    }
    
    /**
     * Getter for type field.
     * @return triggers
     */
    public ArrayList<Abilities> getAbilities() {
        return abilities;
    }
    
    public boolean hasAbility(Abilities ability) {
    	for (Abilities ab: abilities) {
    		if (ab.equals(ability)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean hasNoAbilities() {
    	if (abilities.isEmpty()) {
    		return true;
    	}
    	return false;
    }

    /**
     * toString for Trigger class.
     */
    public String toString() {
        String str = "[";
        for (int i = 0; i < abilities.size(); i++) {
            str+= abilities.get(i);
            if (i + 1 == abilities.size())
                break;
            str+= ", ";
        }
        str += "]";
        return str;
    }
}
