
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

    public void enable(boolean enable) {
        if (!enable) {
            if (es != null) {
                es.shutdownNow();
            }
        } else {
            if (!Files.exists(path)) {
                System.out.println(path.toAbsolutePath() + " doesn't exist.");
                return;
            }
            es = Executors.newSingleThreadExecutor();
            es.submit(() -> {
                while (true) {
                    String readFile = readFile();
                    if (readFile == null) {
                        TimeUnit.SECONDS.sleep(2);
                        continue;
                    }
                    BPanel button = parent.bPanels.get(readFile);
                    if (button == null) {
                        button = new BPanel(readFile, parent);
                        parent.bPanels.put(readFile, button);
                    }
                    button.activate();
                }
            });
        }
    }

    public String readFile() {
        try {
            String content = new String(Files.readAllBytes(path));
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
