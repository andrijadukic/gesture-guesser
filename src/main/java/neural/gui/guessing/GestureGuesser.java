package neural.gui.guessing;

import neural.gui.shared.DrawingListener;
import neural.gui.shared.DrawingTransformer;
import neural.gui.util.IntPoint;
import neural.network.MachineLearningModel;

import java.util.ArrayList;
import java.util.List;

public class GestureGuesser implements DrawingListener, GestureGuesserSubject {

    private final MachineLearningModel model;
    private final DrawingTransformer drawingTransformer;

    private final List<GestureGuesserListener> listeners;

    public GestureGuesser(MachineLearningModel model, DrawingTransformer drawingTransformer) {
        this.model = model;
        this.drawingTransformer = drawingTransformer;
        listeners = new ArrayList<>();
    }

    @Override
    public void onDrawingEnd(List<IntPoint> drawing) {
        if (model != null) {
            double[] output = model.predict(drawingTransformer.transform(drawing));
            notifyGestureGuesserListeners(output);
        }
    }

    @Override
    public void addGestureGuesserListener(GestureGuesserListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeGestureGuesserListener(GestureGuesserListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyGestureGuesserListeners(double[] output) {
        listeners.forEach(listener -> listener.onGestureGuesserConclusion(output));
    }
}
