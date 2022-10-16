package neural.gui.shared;

import neural.gui.util.IntPoint;

import java.util.List;

public interface DrawingListener {

    void onDrawingEnd(List<IntPoint> drawing);
}
