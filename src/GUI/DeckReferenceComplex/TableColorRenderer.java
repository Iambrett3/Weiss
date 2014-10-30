package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableColorRenderer extends JLabel implements TableCellRenderer
{
    private boolean isBordered;

    public TableColorRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object color,
                        boolean isSelected, boolean hasFocus, int row, int column) {
        Color newColor = (Color) color;
        setBackground(newColor);
        //add functionality if it is selected...
        setToolTipText("RGB value: " + newColor.getRed() + ", "
                                     + newColor.getGreen() + ", "
                                     + newColor.getBlue());
        return this;
    }
}
