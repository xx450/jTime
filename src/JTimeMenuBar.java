
import javax.swing.JMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jw635
 */
public final class JTimeMenuBar extends javax.swing.JMenuBar {
    private final JMenu jMenu;
    
    public JTimeMenuBar(JTimeForm parrentFrame){
        super();
        jMenu = new JTimeMenu("Menu",parrentFrame);
        this.add(jMenu);
    }
        
}
