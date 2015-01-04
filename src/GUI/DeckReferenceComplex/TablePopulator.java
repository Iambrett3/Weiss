package GUI.DeckReferenceComplex;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TablePopulator
{
    private static String[] columns = {"Qty", "Number", "Name", "Triggers",
        "Level", "Soul", "Cost"};
    
    private static Integer[] preferredColumnWidths = {0, 80, 300, 80, 0, 0, 0}; //TODO: make this modular
    
    DeckTable table;
    
    public DeckTable createTable() {
        table = new DeckTable();
        table.setModel(new DeckTableModel(columns));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumnSizes();
        return table;
    }
    
    public static Integer getPreferredColumnWidth(int i) {
    	return preferredColumnWidths[i];
    }
    
    public static Integer[] getPreferredColumnWidths() {
        return preferredColumnWidths;
    }
    
    public void setColumnSizes() {
    	for (int i = 0; i < columns.length; i++) {

    		TableColumn column = table.getColumnModel().getColumn(i);
    		column.setPreferredWidth(preferredColumnWidths[i]);
    	}
    }
}
