package neural.gui.collecting;

import javax.swing.*;
import java.awt.*;

public class ClassificationStateStatusBar extends JPanel implements ClassificationStateChangeListener {

    private final JLabel classLabel;
    private final JLabel sampleLabel;

    public ClassificationStateStatusBar() {
        classLabel = new JLabel("Class:");
        sampleLabel = new JLabel("Current sample:");

        initGUI();
    }

    private void initGUI() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(classLabel);
        add(sampleLabel);
    }

    @Override
    public void onStateChange(ClassificationState classificationState) {
        classLabel.setText("Class: " + classificationState.getCurrentClass());
        sampleLabel.setText("Sample: " + classificationState.getCurrentSample());
    }
}
