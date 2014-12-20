package GUI.DeckReferenceComplex;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class SwingHelp {
	/*
     * recursive method for finding the frame that possesses an object.
     */
    public static Frame getOwningFrame(Component comp) {
        if (comp == null) {
          throw new IllegalArgumentException("null Component passed");
        }

        if (comp instanceof Frame) {
          return (Frame) comp;
        }
        return getOwningFrame(SwingUtilities.windowForComponent(comp));
    }
}
