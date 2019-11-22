
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author xx450
 */
public class JTimeForm extends javax.swing.JFrame {

    public static String ENV_JTIME_FILEIN = System.getenv("JTIME_FILEIN");

    Chrono chrono;
    Map<String, BPanel> bPanels = new HashMap<>();
    boolean jTimeAlwaysOnTop = false;
    boolean useJtimeIn = false;
    TPanel tPanel;
    FileFeeder fileWatch;
    JPanel scrollArea;

    public JTimeForm() {
        setSize(100, 300);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        this.tPanel = new TPanel(this);
        this.setTitle("jTime");

        getContentPane().add(tPanel);
        JScrollPane scroller = new JScrollPane();
        scrollArea = new JPanel();
        scrollArea.setLayout(new javax.swing.BoxLayout(scrollArea, javax.swing.BoxLayout.Y_AXIS));
        scroller.add(scrollArea);
        scroller.setViewportView(scrollArea);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scroller);

        this.setJMenuBar(new JTimeMenuBar(this));
        this.fileWatch = new FileFeeder(this);
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
                        form.update();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("interrupted" + ex.getMessage());
                }
            }

        }.start();
    }

    void update() {
        Set<BPanel> newOrder = new TreeSet<>();
        for (Map.Entry<String, BPanel> entry : bPanels.entrySet()) {
            BPanel value = entry.getValue();
            value.updateText();
            newOrder.add(value);
        }

        long grandTotal = 0;

        for (Component component : scrollArea.getComponents()) {
            if (component instanceof BPanel) {
                scrollArea.remove(component);
                grandTotal += ((BPanel) component).getChrono().getTotalRound();
            }
        }

        tPanel.getGrandTotalField().setText(Chrono.formatMillis(grandTotal));

        newOrder.stream().forEach((bPanel) -> {
            scrollArea.add(bPanel);
        });

        redraw();
    }

    void redraw() {
        repaint();
        revalidate();
        bPanels.forEach((k, bPanel) -> {
            if (bPanel.isActive()) {
                scrollArea.scrollRectToVisible(bPanel.getBounds());
            }
        });
    }

    void stopAll() {
        update();
        for (Map.Entry<String, BPanel> entry : bPanels.entrySet()) {
            BPanel value = entry.getValue();
            value.getChrono().stop();
            value.setForeground(new Color(51, 51, 51));
            value.setDefaultColor();
            value.updateText();
            value.setActive(false);
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

    public void setUseJtimeIn(boolean useJtimeIn) {
        this.useJtimeIn = useJtimeIn;
    }

    public boolean isUseJtimeIn() {
        return useJtimeIn;
    }

}
