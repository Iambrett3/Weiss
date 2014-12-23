package GUI.DeckReferenceComplex;

import javax.swing.JTable;

public class TablePopulator
{
    private String[] columns = {"#", "Name", "Number", "Triggers",
        "Color", "Flavor", "Text", "Pack", 
        "Level", "Soul"};
    
    public DeckTable createTable() {
        DeckTable table = new DeckTable();
        table.setModel(new DeckTableModel(columns));
        return table;
    }
}
