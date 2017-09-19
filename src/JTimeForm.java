
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author xx450
 */
public class JTimeForm extends javax.swing.JFrame {

    Chrono chrono;
    Map<String, BPanel> bPanels = new HashMap<>();
    boolean jTimeAlwaysOnTop = false;

    public JTimeForm() {
        setSize(100, 300);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        this.chrono = new Chrono();

        getContentPane().add(new TPanel(this));
        
        this.setJMenuBar(new JTimeMenuBar(this));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
                
        
        final JTimeForm form = new JTimeForm();
        form.setAlwaysOnTop(hasArg(args, "-alwaysOnTop"));

        java.awt.EventQueue.invokeLater(() -> {
            form.setVisible(true);
        });

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        update();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("interrupted" + ex.getMessage());
                }
            }

            void update() {
                Set<BPanel> newOrder = new TreeSet<>();
                for (Map.Entry<String, BPanel> entry : form.bPanels.entrySet()) {
                    BPanel value = entry.getValue();
                    value.updateText();
                    newOrder.add(value);
                }
                
                for (Component component : form.getComponents()) {
                    if (component instanceof BPanel) {
                        form.getContentPane().remove(component);
                    }
                }
                newOrder.stream().forEach((bPanel) -> {
                    form.getContentPane().add(bPanel);
                });
                
                form.redraw();
            }
        }.start();
    }

    void redraw() {
        repaint();
        revalidate();
    }

    void stopAll() {
        for (Map.Entry<String, BPanel> entry : bPanels.entrySet()) {
            BPanel value = entry.getValue();
            value.getChrono().stop();
            value.setForeground(new Color(51, 51, 51));
            value.setDefaultColor();
            value.updateText();
        }
        redraw();
    }
    
    static boolean hasArg(String args[], String wanted) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase(arg)) {
                return true;
            }
        }
        return false;
    }

    public boolean isjTimeAlwaysOnTop() {
        return jTimeAlwaysOnTop;
    }

    public void setjTimeAlwaysOnTop(boolean jTimeAlwaysOnTop) {
        this.jTimeAlwaysOnTop = jTimeAlwaysOnTop;
    }
}
