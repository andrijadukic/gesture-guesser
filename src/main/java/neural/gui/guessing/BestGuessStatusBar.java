package neural.gui.guessing;

import neural.sampling.Sampling;

import javax.swing.*;
import java.awt.*;

public class BestGuessStatusBar extends JPanel implements GestureGuesserListener {

    private final JLabel bestGuessLabel;

    public BestGuessStatusBar() {
        bestGuessLabel = new JLabel("Best guess: ");

        initGUI();
    }

    private void initGUI() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(bestGuessLabel);
    }

    @Override
    public void onGestureGuesserConclusion(double[] output) {
        bestGuessLabel.setText("Best guess: " + Sampling.argMax(output));
    }
}
