package neural.gui.collecting;


import neural.gui.shared.DrawingListener;
import neural.gui.util.IntPoint;
import neural.gui.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DrawingCollector implements DrawingListener {

    private final List<Pair<List<IntPoint>, Integer>> drawings;

    public DrawingCollector() {
        drawings = new ArrayList<>();
    }

    @Override
    public void onDrawingEnd(List<IntPoint> drawing) {
        drawings.add(new Pair<>(drawing, ClassificationState.getState().getCurrentClass()));
    }

    public void clearDrawings() {
        drawings.clear();
    }

    public List<Pair<List<IntPoint>, Integer>> getDrawings() {
        return drawings;
    }
}
