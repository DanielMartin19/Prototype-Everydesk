import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;

public class EveryDeskApp implements Application {
    private Window mainWindow = null;
    private Window remoteWindow = null;
    private BXMLSerializer bxmlSerializer;

    @Override
    public void startup(Display display, org.apache.pivot.collections.Map<String, String> properties) throws Exception {
        bxmlSerializer = new BXMLSerializer();
        
        // 1. MEMUAT HALAMAN UTAMA
        // Pastikan nama file sesuai dengan file BXML yang kamu paste sebelumnya
        mainWindow = (Window) bxmlSerializer.readObject(getClass().getResource("main_ui.bxml"));
        
        // 2. MENCARI TOMBOL "SESSION 1"
        // Kita mencari komponen berdasarkan ID yang kita tambahkan di langkah 1 tadi
        PushButton sessionBtn = (PushButton) bxmlSerializer.getNamespace().get("sessionButton1");

        // 3. MENAMBAHKAN AKSI KLIK (REDIRECT)
        if (sessionBtn != null) {
            sessionBtn.getButtonPressListeners().add(new ButtonPressListener() {
                @Override
                public void buttonPressed(Button button) {
                    try {
                        // Panggil fungsi untuk pindah halaman
                        openRemotePage(display); 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            System.err.println("PERINGATAN: Tombol dengan ID 'sessionButton1' tidak ditemukan di BXML!");
        }
        
        mainWindow.open(display);
    }

    // Fungsi untuk membuka halaman Remote
    private void openRemotePage(Display display) throws Exception {
        // Tutup jendela utama
        if (mainWindow != null) {
            mainWindow.close();
        }

        // Muat halaman Remote UI
        BXMLSerializer remoteSerializer = new BXMLSerializer();
        remoteWindow = (Window) remoteSerializer.readObject(getClass().getResource("remote_ui.bxml"));
        
        // Tambahkan logika tombol Disconnect (Opsional)
        PushButton disconnectBtn = (PushButton) remoteSerializer.getNamespace().get("disconnectButton");
        if (disconnectBtn != null) {
            disconnectBtn.getButtonPressListeners().add(new ButtonPressListener() {
                @Override
                public void buttonPressed(Button button) {
                    remoteWindow.close();
                    mainWindow.open(display); // Buka kembali menu utama
                }
            });
        }

        // Buka jendela remote
        remoteWindow.open(display);
    }

    @Override
    public boolean shutdown(boolean optional) {
        if (mainWindow != null) mainWindow.close();
        if (remoteWindow != null) remoteWindow.close();
        return false;
    }

    @Override
    public void suspend() { }

    @Override
    public void resume() { }

    public static void main(String[] args) {
        DesktopApplicationContext.main(EveryDeskApp.class, args);
    }
}