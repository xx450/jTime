
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author xx450
 */
public class FileFeeder {

    public Path path = Paths.get(JTimeForm.ENV_JTIME_FILEIN != null ? JTimeForm.ENV_JTIME_FILEIN : "jtime.in");
    public String lastContent;
    
    public String[][] mappers = new String[][] {
        {".*(Google Chrome).*", "Google Chrome"},
        {".*(X2GO).*", "X2goClient"}
    };

    public void enable(boolean enable) {
        if (!enable) {
            if (es != null) {
                es.shutdownNow();
            }
        } else {
            if (!Files.exists(path)) {
                JTime.LOG.info(path.toAbsolutePath() + " doesn't exist.");
                return;
            }
            es = Executors.newSingleThreadExecutor();
            es.submit(() -> {
                Thread cThread = Thread.currentThread();
                JTime.LOG.info(cThread + " has just started.");
                while (!cThread.isInterrupted()) {
                    String readFile = readFile();
                    if (readFile == null) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException ex) {
                            cThread.interrupt();
                        }
                        continue;
                    }
                    BPanel button = parent.bPanels.get(readFile);
                    if (button == null) {
                        button = new BPanel(readFile, parent);
                        parent.bPanels.put(readFile, button);
                    }
                    button.activate();
                }
                JTime.LOG.info(cThread + " has ended loop.");
            });
        }
    }

    public String readFile() {
        try {
            String content = new String(Files.readAllBytes(path), "UTF-8");
            for (String[] mapper : mappers) {
                content = content.replaceAll(mapper[0], mapper[1]);
            }
            if (!content.equals(lastContent)) {
                lastContent = content;
                return lastContent;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    ExecutorService es;
    JTimeForm parent;

    public FileFeeder(JTimeForm parent) {
        this.parent = parent;
    }
    
}
