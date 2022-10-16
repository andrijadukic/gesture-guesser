package neural.gui.collecting;

import neural.gui.shared.DrawingListener;
import neural.gui.util.IntPoint;

import java.util.ArrayList;
import java.util.List;

public class ClassificationState implements DrawingListener, ClassificationStateChangeSubject {

    private static ClassificationState classificationState;

    private int currentClass;
    private int currentSample;

    private final List<ClassificationStateChangeListener> listeners;

    private ClassificationState() {
        listeners = new ArrayList<>();
    }

    public static ClassificationState getState() {
        if (classificationState == null) {
            classificationState = new ClassificationState();
        }
        return classificationState;
    }

    public int getCurrentClass() {
        return currentClass;
    }

    public int getCurrentSample() {
        return currentSample;
    }

    public void nextClass() {
        currentClass++;
        currentSample = 0;
        notifyStateChangeListeners();
    }

    public void reset() {
        currentSample = currentClass = 0;
        notifyStateChangeListeners();
    }

    @Override
    public void onDrawingEnd(List<IntPoint> drawing) {
        currentSample++;
        notifyStateChangeListeners();
    }

    @Override
    public void addStateChangeListener(ClassificationStateChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeStateChangeListener(ClassificationStateChangeListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyStateChangeListeners() {
        listeners.forEach(listener -> listener.onStateChange(this));
    }
}
