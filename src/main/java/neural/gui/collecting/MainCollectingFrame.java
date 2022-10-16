package neural.gui.collecting;

import neural.gui.shared.DrawingBoard;
import neural.gui.shared.DrawingTransformer;
import neural.gui.shared.SystemConstants;
import neural.sampling.Sample;
import neural.sampling.Sampling;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class MainCollectingFrame extends JFrame {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;

    private final DrawingBoard drawingBoard;
    private final DrawingCollector drawingCollector;
    private final DrawingTransformer drawingTransformer;

    private final JButton reset;
    private final JButton next;
    private final JButton save;

    private final ClassificationStateStatusBar classificationStateStatusBar;

    public MainCollectingFrame() {
        setTitle("Gesture collector");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        drawingBoard = new DrawingBoard();
        drawingCollector = new DrawingCollector();
        drawingTransformer = new DrawingTransformer(SystemConstants.M);

        classificationStateStatusBar = new ClassificationStateStatusBar();
        ClassificationState.getState().addStateChangeListener(classificationStateStatusBar);

        drawingBoard.addDrawingListener(drawingCollector);
        drawingBoard.addDrawingListener(ClassificationState.getState());

        reset = new JButton("Reset");
        next = new JButton("Next");
        save = new JButton("Save");

        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(drawingBoard, BorderLayout.CENTER);
        cp.add(classificationStateStatusBar, BorderLayout.PAGE_START);

        JPanel lowerPanel = new JPanel();

        reset.setFocusable(false);
        reset.addActionListener(e -> {
            drawingCollector.clearDrawings();
            ClassificationState.getState().reset();
            next.setEnabled(true);
        });

        next.setFocusable(false);
        next.addActionListener(e -> {
            ClassificationState state = ClassificationState.getState();
            state.nextClass();
            if (state.getCurrentClass() == (SystemConstants.CLASS_COUNT_LIMIT - 1)) {
                next.setEnabled(false);
            }
        });

        save.setFocusable(false);
        save.addActionListener(e -> {
            try {
                Sampling.write(
                        drawingCollector.getDrawings().stream()
                                .map(drawingLabelPair -> new Sample(
                                        drawingTransformer.transform(drawingLabelPair.first()),
                                        Sampling.oneHotEncode(drawingLabelPair.second(), SystemConstants.CLASS_COUNT_LIMIT)
                                ))
                                .collect(Collectors.toList()),
                        SystemConstants.DATASET_PATH
                );
                JOptionPane.showMessageDialog(this, "Samples saved successfully", "Save status", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while saving samples", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        lowerPanel.add(reset);
        lowerPanel.add(next);
        lowerPanel.add(save);

        cp.add(lowerPanel, BorderLayout.PAGE_END);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
