package Search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;

import WeissSchwarz.DeckFileHandler;
import Card.Ability;
import Card.CColor;
import Card.Card;
import Card.Chara;
import Card.Climax;
import Card.Event;
import Card.Trait;
import Card.Trigger;
import GUI.BuilderController;
import GUI.DeckReferenceComplex.SwingHelp;
import net.miginfocom.swing.MigLayout;

public class SearchDialog extends JDialog{
	JTextField searchBar;
	JButton searchButton;
	JButton resetButton;
	
	JCheckBox engNameBox;
	JCheckBox jpnNameBox;
	JCheckBox numBox;
	JCheckBox cardTextBox;
	JCheckBox flavorBox;
	
	JCheckBox[] colorBoxes;
	
	JComboBox typeCombo;
	JComboBox titleCombo;
	
	ButtonGroup powerGroup;
	
	JComboBox powerCombo;
	JRadioButton atLeast;
	JRadioButton exactly;
	JRadioButton lessThan;
	
	JComboBox costCombo;
	JComboBox soulCombo;
	JComboBox rarityCombo;
	JComboBox levelCombo;
	
	JComboBox trigger1Combo;
	JComboBox trigger2Combo;
	JComboBox trait1Combo;
	JComboBox trait2Combo;
	
	JCheckBox redBox;
	JCheckBox greenBox;
	JCheckBox yellowBox;
	JCheckBox blueBox;
	
	ArrayList<JCheckBox> abilityCheckBoxes;
	BuilderController controller;
	
	public SearchDialog(Frame parent, BuilderController controller) {
		super(parent, false);
		this.controller = controller;
		setAlwaysOnTop(false);
		setLayout(new BorderLayout());
		JPanel thePanel = new JPanel(new MigLayout());
		thePanel.add(getSearchPanel(), "wrap");
		thePanel.add(getTypeAndTitlePanel(), "wrap");
		thePanel.add(getColorRarityCostSoulLevelPanel(), "wrap");
		thePanel.add(getTriggersTraitsAndAbilitiesPanel(), "wrap"); //triggers traits and abilities
		thePanel.add(getPowerPanel());
		JScrollPane theScroll = new JScrollPane(thePanel);
		add(theScroll);
		setTitle("Card Database Search");
		pack();
		setKeyListeners();
		setVisible(true);
	}
	
	public void setKeyListeners() {
		searchBar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
				switch (ke.getKeyCode()) {
					case KeyEvent.VK_ENTER: search();
					break;
				}
			}
			public void keyTyped(KeyEvent ke) {
				
			}
			public void keyReleased(KeyEvent ke) {
	
			}
		});
	}
	
	public void search() {
		SearchObject searchObject = generateSearchObject();
		ArrayList<Card> results = Searcher.search(searchObject);
		showResultsDialog(results);
	}
	
	public void showResultsDialog(ArrayList<Card> cards) {
		SearchResultsDialog results = new SearchResultsDialog(cards, SwingHelp.getOwningFrame(this));
		results.setTitle("Search Results"); //TODO: make this reflect the inputs of the search.
		controller.registerSearchResultsDialog(results);
		results.setVisible(true);
	}
	
	public void resetAllFields() {
		resetLevelCardOnlyFields();
		
		searchBar.setText("Enter search terms...");
		searchBar.setForeground(Color.GRAY);
		
		engNameBox.setSelected(false);
		jpnNameBox.setSelected(false);
		numBox.setSelected(false);
		cardTextBox.setSelected(false);
		flavorBox.setSelected(false);
		
		typeCombo.setSelectedIndex(0);
		titleCombo.setSelectedIndex(0);
		
		redBox.setSelected(false);
		greenBox.setSelected(false);
		yellowBox.setSelected(false);
		blueBox.setSelected(false);
		
		rarityCombo.setSelectedIndex(0);
		
		trigger1Combo.setSelectedIndex(0);
		trigger2Combo.setSelectedIndex(0);
		
	}
	
	public void resetLevelCardOnlyFields() {
		resetCharacterOnlyFields();
		costCombo.setSelectedIndex(0);
		levelCombo.setSelectedIndex(0);
		clearAbilities();
	}
	
	public void clearAbilities() {
		for (JCheckBox abilityBox: abilityCheckBoxes) {
			abilityBox.setSelected(false);
		}
	}
	
	public void resetCharacterOnlyFields() {
		soulCombo.setSelectedIndex(0);
		trait1Combo.setSelectedIndex(0);
		trait2Combo.setSelectedIndex(0);
		atLeast.setSelected(true);
		powerCombo.setSelectedIndex(0);
	}
	
	public CColor[] getSelectedColors() {
		ArrayList<CColor> selectedColors = new ArrayList<CColor>();
		for (JCheckBox colorBox: colorBoxes) {
			if (colorBox.isSelected()) {
				selectedColors.add(new CColor(colorBox.getText()));
			}
		}
		
		CColor[] colors = new CColor[selectedColors.size()];
		int i = 0;
		for (CColor color: selectedColors) {
			colors[i++] = color;
		}
		return colors;
	}
	
	public SearchObject generateSearchObject() {

		return new SearchObject(engNameBox.isSelected(), jpnNameBox.isSelected(), numBox.isSelected(),
				cardTextBox.isSelected(), flavorBox.isSelected(), (Class)((ComboItem)typeCombo.getSelectedItem()).getValue(), 
				(String) titleCombo.getSelectedItem(), getSelectedColors(),  (String) rarityCombo.getSelectedItem(),
				getIntegerComboValue(costCombo), getIntegerComboValue(soulCombo),
				getIntegerComboValue(levelCombo), (String) trigger1Combo.getSelectedItem(), (String) trigger2Combo.getSelectedItem(),
				(String) trait1Combo.getSelectedItem(), (String) trait2Combo.getSelectedItem(),
				getAbilitySelection(), new PowerHelper(getPowerState(), (Integer) powerCombo.getSelectedItem()),
				searchBar.getText()); 
	}
	
	public Integer getIntegerComboValue(JComboBox combo) {
		if (combo.getSelectedItem() instanceof Integer) {
			return (Integer) combo.getSelectedItem();
		}
		else return -1;
	}
	
	public String getPowerState() {
		if (atLeast.isSelected())
			return atLeast.getText();
		if (exactly.isSelected()) 
			return exactly.getText();
		else
			return lessThan.getText();
	}
	
	public Ability getAbilitySelection() {
		Ability abilitySelection = new Ability();
		for (JCheckBox abilityBox: abilityCheckBoxes) {
			if (abilityBox.isSelected()) {
				abilitySelection.addAbility(Ability.stringToType(abilityBox.getText()));
			}
		}
		return abilitySelection;
	}
	
	public JPanel getTypeAndTitlePanel() {
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new MigLayout());
				
		ComboItem[] types = {new ComboItem(null, "Any Type"), new ComboItem(Chara.class, "Character"),
							new ComboItem(Climax.class, "Climax"), new ComboItem(Event.class, "Event")};
		typeCombo = new JComboBox(types);
		typeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox sourceBox = (JComboBox) e.getSource();
				ComboItem selected = (ComboItem) sourceBox.getSelectedItem();
				
				switch(selected.getLabel()) {
				case "Climax":  resetLevelCardOnlyFields();
							    costCombo.setEnabled(false);
							    levelCombo.setEnabled(false);
								powerCombo.setEnabled(false); 
								soulCombo.setEnabled(false);
								trait1Combo.setEnabled(false);
								trait2Combo.setEnabled(false);
								enablePowerGroup(false);
								enableAbilities(false);
								break;
				case "Event":	resetCharacterOnlyFields();
								costCombo.setEnabled(true);
								levelCombo.setEnabled(true);
								powerCombo.setEnabled(false);
								soulCombo.setEnabled(false);
								trait1Combo.setEnabled(false);
								trait2Combo.setEnabled(false);
								enablePowerGroup(false);
								enableAbilities(true);
								break;
				default:		costCombo.setEnabled(true);
								levelCombo.setEnabled(true);
								powerCombo.setEnabled(true);
								soulCombo.setEnabled(true);
								trait1Combo.setEnabled(true);
								trait2Combo.setEnabled(true);
								enablePowerGroup(true);
								enableAbilities(true);
				}
			}
		});
		
		
		String[] titles = getTitles();
		titleCombo = new JComboBox(titles);
		
		cardPanel.add(typeCombo);
		cardPanel.add(titleCombo);
		return cardPanel;
		
	}
	
	public void enableAbilities(boolean enable) {
		for (JCheckBox abilityBox: abilityCheckBoxes) {
			abilityBox.setEnabled(enable);
		}
	}
	
	public JPanel getPowerPanel() {
		JPanel powerPanel = new JPanel(new MigLayout());
		
		powerPanel.add(new JLabel("Power:"), "wrap");
		
		JPanel buttonPanel = new JPanel(new MigLayout());
		atLeast = new JRadioButton("At least:", true);
		exactly = new JRadioButton("Exactly:", true);
		lessThan = new JRadioButton("Less Than:", true);

		
		
		powerGroup = new ButtonGroup();
		powerGroup.add(atLeast);
		powerGroup.add(exactly);
		powerGroup.add(lessThan);
		
		buttonPanel.add(atLeast, "wrap");
		buttonPanel.add(exactly, "wrap");
		buttonPanel.add(lessThan);
		
		powerPanel.add(buttonPanel);
		
		JPanel powerSelectionPanel = new JPanel(new MigLayout());
		
		final int HIGHEST_POWER = 20000;
		
		Integer[] powerLevels = new Integer[HIGHEST_POWER/500];
		int power = 0;
		for(int i = 0; i < powerLevels.length; i++) {
			powerLevels[i] = power;
			power += 500;
		}
		
		
		powerSelectionPanel.add(powerCombo = new JComboBox(powerLevels));
		
		powerPanel.add(powerSelectionPanel);
		return powerPanel;
	}
	
	public void enablePowerGroup(boolean enable) {
			atLeast.setEnabled(enable);
			exactly.setEnabled(enable);
			lessThan.setEnabled(enable);
	}
	
	public JPanel getColorRarityCostSoulLevelPanel() {
		JPanel thePanel = new JPanel(new MigLayout());
		thePanel.add(getColorPanel());
		thePanel.add(getCostSoulLevelAndRarityPanel());
		return thePanel;
	}
	
	public JPanel getCostSoulLevelAndRarityPanel() {
		JPanel costSoulLevelAndRarityPanel = new JPanel(new MigLayout());
		
		JPanel costPanel = new JPanel(new MigLayout());
		costPanel.add(new JLabel("Cost:"), "wrap");
		Object[] costs = {"All", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		costPanel.add(costCombo = new JComboBox(costs));
		costSoulLevelAndRarityPanel.add(costPanel);
		
		JPanel soulPanel = new JPanel(new MigLayout());
		soulPanel.add(new JLabel("Soul:"), "wrap");
		Object[] souls = {"All", 0, 1, 2, 3, 4, 5};
		soulPanel.add(soulCombo = new JComboBox(souls));
		costSoulLevelAndRarityPanel.add(soulPanel);
		
		JPanel levelPanel = new JPanel(new MigLayout());
		levelPanel.add(new JLabel("Level:"), "wrap");
		Object[] levels = {"All", 0, 1, 2, 3, 4};
		levelPanel.add(levelCombo = new JComboBox(levels));
		costSoulLevelAndRarityPanel.add(levelPanel);
		
		JPanel rarityPanel = new JPanel();
		rarityPanel.setLayout(new MigLayout());
		rarityPanel.add(new JLabel("Rarity:"), "wrap");
		String[] rarities = {"All", "C", "U", "R", "RR", "SR", "RRR", "CC", "CR", "SP", "TD", "PR"};
		rarityPanel.add(rarityCombo = new JComboBox(rarities));
		costSoulLevelAndRarityPanel.add(rarityPanel);
		
		return costSoulLevelAndRarityPanel;
	}
	
	public JPanel getSearchPanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new MigLayout());
		searchBar = new JTextField("Enter search terms...");
		searchBar.setForeground(Color.GRAY);
		searchBar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (searchBar.getText().equals("Enter search terms...")) {
					searchBar.setText("");
					searchBar.setForeground(Color.BLACK);
				}
			}
		});
		searchBar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
				if (searchBar.getText().equals("Enter search terms...")) {
					searchBar.setText("");
					searchBar.setForeground(Color.BLACK);
				}
			}
			public void keyTyped(KeyEvent ke) {};
			public void keyReleased(KeyEvent ke) {};
		});
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		
		JButton resetButton = new JButton("Reset Fields");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAllFields();
			}
		});
		
		JPanel buttonPanel = new JPanel(new MigLayout());
		buttonPanel.add(searchButton, "wrap");
		buttonPanel.add(resetButton);
		
		searchBar.setPreferredSize(new Dimension(500, 20));
		
		
		JPanel searchBarPanel = new JPanel(new MigLayout());
		searchBarPanel.add(searchBar);
		searchBarPanel.add(buttonPanel);
		
		searchPanel.add(searchBarPanel, "wrap");
		
		engNameBox = new JCheckBox("Name (English)");
		jpnNameBox = new JCheckBox("Name (Japanese)");
		numBox = new JCheckBox("Number");
		cardTextBox = new JCheckBox("Text");
		flavorBox = new JCheckBox("Flavor");
		
		JPanel searchBoxPanel = new JPanel();
		searchBoxPanel.setLayout(new MigLayout());
		searchBoxPanel.add(new JLabel("Search by"), "wrap");
		searchBoxPanel.add(engNameBox);
		searchBoxPanel.add(jpnNameBox);
		searchBoxPanel.add(numBox);
		searchBoxPanel.add(cardTextBox);
		searchBoxPanel.add(flavorBox);
		
//		engNameBox.addItemListener(this);
//		jpnNameBox.addItemListener(this);
//		numBox.addItemListener(this);
//		cardTextBox.addItemListener(this);
		
		searchPanel.add(searchBoxPanel);
		return searchPanel;
	}
	
	public JPanel getTriggersTraitsAndAbilitiesPanel() {
		JPanel triggerPanel = new JPanel();
		triggerPanel.setLayout(new MigLayout());
		
		JPanel triggerAndTraitPanel = new JPanel();
		triggerAndTraitPanel.setLayout(new MigLayout());
		
		JPanel triggerComboPanel = new JPanel();
		triggerComboPanel.setLayout(new MigLayout());
		triggerComboPanel.add(new JLabel("Triggers:"), "wrap");
		trigger1Combo = new JComboBox(getTypeList());
		trigger2Combo = new JComboBox(getTypeList());
		triggerComboPanel.add(trigger1Combo);
		triggerComboPanel.add(trigger2Combo);
		triggerAndTraitPanel.add(triggerComboPanel);
		
		JPanel traitComboPanel = new JPanel();
		traitComboPanel.setLayout(new MigLayout());
		traitComboPanel.add(new JLabel("Traits:"), "wrap");
		trait1Combo = new JComboBox(getTraitList());
		trait2Combo = new JComboBox(getTraitList());
		traitComboPanel.add(trait1Combo);
		traitComboPanel.add(trait2Combo);
		triggerAndTraitPanel.add(traitComboPanel);
		
		triggerPanel.add(triggerAndTraitPanel, "wrap");
		
		JPanel abilitiesPanel = new JPanel();
		abilitiesPanel.setLayout(new MigLayout());
		abilitiesPanel.add(new JLabel("Abilities:"), "wrap");
		String[] abilities = Ability.getListOfAbilities();
		abilityCheckBoxes = new ArrayList<JCheckBox>();
		int numAdded = 0;
		for (String ab : abilities) {
			JCheckBox box;
			if (numAdded > 3) {
				abilitiesPanel.add(box = new JCheckBox(ab), "wrap");
				numAdded = 0;
			}
			else {
				abilitiesPanel.add(box = new JCheckBox(ab));
				numAdded++;
			}
			abilityCheckBoxes.add(box);
		}
		triggerPanel.add(abilitiesPanel);
		return triggerPanel;
	}
	
	public String[] getTypeList() {
		String[] list = new String[Trigger.Type.getTypeArray().length + 1];
		list[0] = "All";
		System.arraycopy(Trigger.Type.getTypeStringArray(), 0, list, 1, Trigger.Type.getTypeArray().length);
		return list;
	}
	
	public String[] getTraitList() {
		String[] list = new String[Trait.getTraitList().length + 1];
		list[0] = "All";
		System.arraycopy(Trait.getTraitList(), 0, list, 1, Trait.getTraitList().length);
		return list;
	}
	
	public JPanel getColorPanel() {
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new MigLayout());
		
		colorBoxes = new JCheckBox[4];
		
		redBox = new JCheckBox("Red");
		colorBoxes[0] = redBox;
		greenBox = new JCheckBox("Green");
		colorBoxes[1] = greenBox;
		yellowBox = new JCheckBox("Yellow");
		colorBoxes[2] = yellowBox;
		blueBox = new JCheckBox("Blue");
		colorBoxes[3] = blueBox;
		
		JPanel colorBoxPanel = new JPanel();
		colorBoxPanel.setLayout(new MigLayout());
		colorBoxPanel.add(new JLabel("Color:"), "wrap");
		colorBoxPanel.add(redBox);
		colorBoxPanel.add(greenBox);
		colorBoxPanel.add(yellowBox);
		colorBoxPanel.add(blueBox);
		
		colorPanel.add(colorBoxPanel);
		
		return colorPanel;
	}
	

	
	public String[] getTitles() {
		File titlesFile = new File(DeckFileHandler.getDatabaseFilePath() + "TitleList.txt");
		ArrayList<String> titleList = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(titlesFile);
			while (scan.hasNext()) {
				titleList.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("getTitles failed in Search Dialog");
		}
		Collections.sort(titleList);
		titleList.add(0, "All Titles");
		
		String[] titles = new String[titleList.size()];
		int i = 0;
		for (String title : titleList) {
			titles[i++] = title;
		}
		return titles;
	}

}
