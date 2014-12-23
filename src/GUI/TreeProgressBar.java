package GUI;

import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class TreeProgressBar extends JDialog
{
    JProgressBar bar;
    int progress;
    
    public TreeProgressBar(int lengthOfTask) {
        setBounds(90, 90, 100, 200);
        bar = new JProgressBar(0, lengthOfTask);
        progress = 0;
        bar.setValue(progress);
        bar.setStringPainted(true);
        add(bar);
        setVisible(true);
    }
    
    public void incrementProgress() {
        bar.setValue(++progress);
    }
}
