package neural.gui.collecting;

import javax.swing.*;

public class GestureCollectorApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainCollectingFrame().setVisible(true));
    }
}
