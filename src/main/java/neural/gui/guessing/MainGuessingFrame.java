package neural.gui.guessing;

import neural.gui.shared.DrawingBoard;
import neural.gui.shared.DrawingTransformer;
import neural.gui.shared.SystemConstants;
import neural.network.MachineLearningModel;

import javax.swing.*;
import java.awt.*;

public class MainGuessingFrame extends JFrame {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;

    private final DrawingBoard drawingBoard;
    private final BestGuessStatusBar bestGuessStatusBar;

    public MainGuessingFrame(MachineLearningModel model) {
        setTitle("Gesture guesser");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        drawingBoard = new DrawingBoard();
        GestureGuesser guesser = new GestureGuesser(model, new DrawingTransformer(SystemConstants.M));
        bestGuessStatusBar = new BestGuessStatusBar();

        guesser.addGestureGuesserListener(bestGuessStatusBar);
        guesser.addGestureGuesserListener(new StandardOutputPredictionLogger());
        drawingBoard.addDrawingListener(guesser);

        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(drawingBoard, BorderLayout.CENTER);
        cp.add(bestGuessStatusBar, BorderLayout.PAGE_START);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
