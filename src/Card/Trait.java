/**
 * Trait.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
package Card;
import java.util.ArrayList;
/**
 * Trait class.
 */
public class Trait {
private ArrayList<String> traits;

/**
 * no-arg constructor for Trait object. Initializes traits ArrayList.
 */
public Trait() {
	traits = new ArrayList<>();
}

/**
 * Constructor for Trait object with one trait.
 * @param trait Trait
 */
public Trait(String trait) {
	traits = new ArrayList<>(1);
	traits.add(trait);
}

/**
 * Constructor for Trait object with two traits.
 * @param trait Trait
 * @param trait2 Trait 2
 */
public Trait(String trait, String trait2) {
	traits = new ArrayList<>(2);
	traits.add(trait);
	traits.add(trait2);
}

/**
 * Adds a trait.
 * @param trait Trait
 */
public void addTrait(String trait) {
	traits.add(trait);
}

/**
 * toString method for Trait class.
 */
public String toString() {
	return traits.toString();
}
}
