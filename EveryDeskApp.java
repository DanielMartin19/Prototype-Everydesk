import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

public class EveryDeskApp implements Application {
    private Window window = null;

    @Override
    public void startup(Display display, org.apache.pivot.collections.Map<String, String> properties) throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        
        // Memuat file main_ui.bxml
        // Pastikan file bxml berada di folder yang sama atau classpath yang benar
        window = (Window) bxmlSerializer.readObject(getClass().getResource("main_ui.bxml"));
        
        window.open(display);
    }

    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }
        return false;
    }

    @Override
    public void suspend() {
    }

    @Override
    public void resume() {
    }

    public static void main(String[] args) {
        // Menjalankan aplikasi desktop
        DesktopApplicationContext.main(EveryDeskApp.class, args);
    }
}