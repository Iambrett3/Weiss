package GUI.DeckReferenceComplex;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TablePopulator
{
    private static String[] columns = {"Qty", "Number", "Name", "Triggers",
        "Level", "Soul", "Cost"};
    
    private static Integer[] preferredColumnWidths = {-1, 75, 150, 50, 0, 0, 0}; //TODO: make this modular
    
    DeckTable table;
    
    public DeckTable createTable() {
        table = new DeckTable();
        table.setModel(new DeckTableModel(columns));
        //setColumnSizes();
        return table;
    }
    
    public static Integer getPreferredColumnWidth(int i) {
    	return preferredColumnWidths[i];
    }
    
    public void setColumnSizes() {
    	for (int i = 0; i < columns.length; i++) {

    		TableColumn column = table.getColumnModel().getColumn(i);
    		column.setPreferredWidth(preferredColumnWidths[i]);
    	}
    }
}
