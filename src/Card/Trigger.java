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
public class Trigger {
	/**
	 * Enum types for trigger.
	 *
	 */
	public enum Type { SOUL, DOUBLE_SOUL, RETURN, POOL,
	            COME_BACK, DRAW, SHOT, TREASURE }
	
	private ArrayList<Type> triggers;
	
	/**
	 * Constructor for Trigger object with one trigger type.
	 * @param type Type
	 */
	public Trigger(Type type) {
		triggers = new ArrayList<>(1);
		triggers.add(type);
	}
	
	/**
	 * Constructor for Trigger object with two trigger types.
	 * @param type Type
	 */
	public Trigger(Type type, Type type2) {
		triggers = new ArrayList<>(2);
		triggers.add(type);
		triggers.add(type2);
	}
	
	/**
	 * Constructor for Trigger object with an Array of triggers represented 
	 * as Strings.
	 * @param types String[] of triggers represented as strings
	 */
	public Trigger(String[] types) {
		triggers = new ArrayList<>(types.length);
		for (String trigger : types) {
			triggers.add(stringToType(trigger));
		}
			
	}
	
	/**
	 * Converts string to Trigger type.
	 * @param color Color
	 */
	public Type stringToType(String trigger) {
		trigger = trigger.toLowerCase();
		Type type = null;
		if (trigger.equals("soul"))
			type = Type.SOUL;
		if (trigger.equals("2soul"))
			type = Type.DOUBLE_SOUL;
		if (trigger.equals("bounce"))
			type = Type.RETURN;
		if (trigger.equals("stock"))
			type = Type.POOL;
		if (trigger.equals("salvage"))
			type = Type.COME_BACK;
		if (trigger.equals("draw"))
			type = Type.DRAW;
		if (trigger.equals("shot"))
			type = Type.SHOT;
		if (trigger.equals("treasure"))
			type = Type.TREASURE;
		return type;
	}
	
	/**
	 * Setter for type field.
	 * @param triggers Triggers
	 */
	public void setTriggers(ArrayList<Type> triggers) {
		this.triggers = triggers;
	}
	
	/**
	 * Getter for type field.
	 * @return triggers
	 */
	public ArrayList<Type> getTriggers() {
		return triggers;
	}

	/**
	 * toString for Trigger class.
	 */
	public String toString() {
		return triggers.toString();
	}
}
