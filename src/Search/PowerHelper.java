package Search;

public class PowerHelper {
	
	private String state;
	private Integer power;
	
	public PowerHelper(String state, Integer power) {
		this.state = state;
		this.power = power;
	}
	
	public String getState() {
		return state;
	}
	
	public Integer getPower() {
		return power;
	}

	public String toString() {
		return state + " " + power;
	}
}
