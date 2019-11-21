import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author jw635
 */
public final class JTimeMenu extends JMenu {
    private final JTimeForm parrentFrame;
    
    public JTimeMenu(String label, JTimeForm parrentFrame){
        super(label);
        this.parrentFrame = parrentFrame;              
        this.add(initUndecorated());
        this.add(initAlwaysOnTop());
        this.add(initJtimeIn());
    }
    
    JMenuItem initUndecorated(){
        JMenuItem undecorated = new JCheckBoxMenuItem("Undecorated");
        undecorated.setVisible(true);
        undecorated.addActionListener((ActionEvent e) -> {
            parrentFrame.dispose();
            parrentFrame.setUndecorated(undecorated.isSelected());
            parrentFrame.setVisible(true);
        });     
        return undecorated;
    }
        
    JMenuItem initAlwaysOnTop(){
        JMenuItem alwaysOnTop = new JCheckBoxMenuItem("Always on Top");
        alwaysOnTop.setVisible(true);
        alwaysOnTop.addActionListener((ActionEvent e) -> {
            parrentFrame.setjTimeAlwaysOnTop(!parrentFrame.isjTimeAlwaysOnTop());
            parrentFrame.setAlwaysOnTop(parrentFrame.isjTimeAlwaysOnTop());
            alwaysOnTop.setArmed(parrentFrame.isjTimeAlwaysOnTop());
        });
        
        return alwaysOnTop;
    }
    
    JMenuItem initJtimeIn() {
        JMenuItem jtimeIn = new JCheckBoxMenuItem("Use jtime.in");
        jtimeIn.setVisible(true);
        jtimeIn.addActionListener((ActionEvent e) -> {
            parrentFrame.setUseJtimeIn(!parrentFrame.isjTimeAlwaysOnTop());
            parrentFrame.fileWatch.enable(parrentFrame.isUseJtimeIn());
        });
        
        return jtimeIn;
    }
    
    
}
