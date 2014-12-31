/**
 * Trait.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
package Card;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Trait class.
 */
public class Trait implements Serializable {
private ArrayList<String> traits;

public static String[] getTraitList() {
	return new String[]{"None", "Alien", "Angel", "Animal", "Banana", "Blacksmith", 
			"Blood", "Book", "Brigade Chief", "Bug", "Camera", "Chairman", "Clone"};
}

/**
 * no-arg constructor for Trait object. Initializes traits ArrayList.
 */
public Trait() {
	traits = new ArrayList<>(2);
	traits.add("None");
	traits.add("None");
}

/**
 * Constructor for Trait object with one trait.
 * @param trait Trait
 */
public Trait(String trait) {
	traits = new ArrayList<>(2);
	traits.add(trait);
	traits.add("None");
}

public ArrayList<String> getTraits() {
	return traits;
}

/**
 * Constructor for Trait object with two traits.
 * @param trait Trait
 * @param trait2 Trait 2
 */
public Trait(String trait, String trait2) {
	traits = new ArrayList<>();
	traits.add(trait);
	traits.add(trait2);
}

public String getTrait1() {
	return traits.get(0);
}

public String getTrait2() {
	return traits.get(1);
}

/**
 * Adds a trait.
 * @param trait Trait
 */
public void setTrait(int pos, String trait) {
	traits.set(pos, trait);
}

public boolean hasTrait(String trait) {
	for (String t: traits) {
		if (trait.equals(t)) {
			return true;
		}
	}
	return false;
}

/**
 * toString method for Trait class.
 */
public String toString() {
	return traits.toString();
}
}
