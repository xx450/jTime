
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jw635
 */
public final class JTimeMenu extends JMenu {
    private final JTimeForm parrentFrame;
    
    public JTimeMenu(String label, JTimeForm parrentFrame){
        super(label);
        this.parrentFrame = parrentFrame;              
        this.add(initAlwaysOnTop());
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
    
    
}
