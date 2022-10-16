package neural.gui.shared;

public interface DrawingCreator {

    void addDrawingListener(DrawingListener listener);

    void removeDrawingListener(DrawingListener listener);

    void notifyDrawingListeners();
}
