package GUI.DeckReferenceComplex;

import javax.swing.table.TableRowSorter;

public class DeckTableSorter extends TableRowSorter {
    
    public DeckTableSorter(DeckTableModel model) {
        super(model);
    }
}
