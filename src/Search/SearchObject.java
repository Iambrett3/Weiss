package Search;

import java.awt.Color;
import java.util.Arrays;

import Card.Ability;
import Card.CColor;
import Card.Trait;
import Card.Trigger;

public class SearchObject {
	private boolean engName;
	private boolean jpnName;
	private boolean number;
	private boolean flavor;
	private boolean cardText;
	private Class type;
	private String title;
	private CColor[] colors;
	private String rarity;
	private Integer cost;
	private Integer soul;
	private Integer level;
	private String trigger1;
	private String trigger2;
	private String trait1;
	private String trait2;
	private Ability abilities;
	private String searchInput;
	private PowerHelper power;
	private boolean textEntered;
	private boolean isTextInputFilter;
	
	public SearchObject(boolean engName, boolean jpnName, boolean number, boolean cardText,
			boolean flavor, Class type, String title, CColor[] colors,
			String rarity, Integer cost, Integer soul, Integer level,
			String trigger1, String trigger2, String trait1, String trait2, Ability abilities, PowerHelper power, String searchInput) {
		super();
		this.cardText = cardText;
		this.engName = engName;
		this.jpnName = jpnName;
		this.number = number;
		this.flavor = flavor;
		this.type = type;
		this.title = title;
		this.colors = colors;
		this.rarity = rarity;
		this.cost = cost;
		this.soul = soul;
		this.level = level;
		this.trigger1 = trigger1;
		this.trigger2 = trigger2;
		this.trait1 = trait1;
		this.trait2 = trait2;
		this.abilities = abilities;
		this.power = power;
		this.searchInput = searchInput;
		if (searchInput.equals("Enter search terms...")) {
			textEntered = false;
		}
		else {
			textEntered = true;
		}
		if (!cardText && !engName && !jpnName && !number && !flavor) {
			isTextInputFilter = false;
		}
		else {
			isTextInputFilter = true;
		}
	}

	public boolean getEngName() {
		return engName;
	}
	
	public boolean getCardText() {
		return cardText;
	}
	
	public boolean isTextEntered() {
		return textEntered;
	}
	
	public void setCardText(boolean cardText) {
		this.cardText = cardText;
	}

	public CColor[] getColors() {
		return colors;
	}

	public void setColors(CColor[] colors) {
		this.colors = colors;
	}

	public PowerHelper getPower() {
		return power;
	}

	public void setPower(PowerHelper power) {
		this.power = power;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

	public boolean isTextInputFilter() {
		return isTextInputFilter;
	}

	public String getSearchInput() {
		return searchInput;
	}
	
	public void setSearchinput(String searchInput) {
		this.searchInput = searchInput;
	}

	public void setEngName(boolean engName) {
		this.engName = engName;
	}

	public boolean getJpnName() {
		return jpnName;
	}

	public void setJpnName(boolean jpnName) {
		this.jpnName = jpnName;
	}

	public boolean getNumber() {
		return number;
	}

	public void setNumber(boolean number) {
		this.number = number;
	}

	public boolean getFlavor() {
		return flavor;
	}

	public void setFlavor(boolean flavor) {
		this.flavor = flavor;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CColor[] getColor() {
		return colors;
	}

	public void setColor(CColor[] colors) {
		this.colors = colors;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getSoul() {
		return soul;
	}

	public void setSoul(Integer soul) {
		this.soul = soul;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}



	public String getTrigger1() {
		return trigger1;
	}

	public void setTrigger1(String trigger1) {
		this.trigger1 = trigger1;
	}

	public String getTrigger2() {
		return trigger2;
	}

	public void setTrigger2(String trigger2) {
		this.trigger2 = trigger2;
	}

	public String getTrait1() {
		return trait1;
	}

	public void setTrait1(String trait1) {
		this.trait1 = trait1;
	}

	public String getTrait2() {
		return trait2;
	}

	public void setTrait2(String trait2) {
		this.trait2 = trait2;
	}

	public void setTextEntered(boolean textEntered) {
		this.textEntered = textEntered;
	}

	@Override
	public String toString() {
		return "SearchObject [engName=" + engName + ", jpnName=" + jpnName
				+ ", number=" + number + ", flavor=" + flavor + ", cardText="
				+ cardText + ", type=" + type + ", title=" + title
				+ ", colors=" + Arrays.toString(colors) + ", rarity=" + rarity
				+ ", cost=" + cost + ", soul=" + soul + ", level=" + level
				+ ", trigger1=" + trigger1 + ", trigger2=" + trigger2
				+ ", trait1=" + trait1 + ", trait2=" + trait2 + ", abilities="
				+ abilities + ", searchInput=" + searchInput + ", power="
				+ power + ", textEntered=" + textEntered + "]";
	}

	public Ability getAbilities() {
		return abilities;
	}

	public void setAbilities(Ability abilities) {
		this.abilities = abilities;
	}
	
	
	
}
