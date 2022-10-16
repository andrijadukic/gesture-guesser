package neural.gui.shared;

import neural.exceptions.InsufficientSampleSizeException;
import neural.gui.util.DoublePoint;
import neural.gui.util.IntPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawingTransformer {

    private final int M;

    public DrawingTransformer(int M) {
        this.M = M;
    }

    public double[] transform(List<IntPoint> drawing) {
        if (drawing.size() < M) throw new InsufficientSampleSizeException(M, drawing.size());

        double meanX = drawing.stream().mapToDouble(IntPoint::x).average().orElseThrow(IllegalStateException::new);
        double meanY = drawing.stream().mapToDouble(IntPoint::y).average().orElseThrow(IllegalStateException::new);

        List<DoublePoint> translated = drawing.stream().map(point -> new DoublePoint(point.x() - meanX, point.y() - meanY)).collect(Collectors.toList());

        double maxX = translated.stream().mapToDouble(p -> Math.abs(p.x())).max().orElseThrow(IllegalStateException::new);
        double maxY = translated.stream().mapToDouble(p -> Math.abs(p.y())).max().orElseThrow(IllegalStateException::new);
        double max = Math.max(maxX, maxY);

        List<DoublePoint> scaled = translated.stream().map(p -> new DoublePoint(p.x() / max, p.y() / max)).collect(Collectors.toList());

        double gestureLength = 0;
        for (int i = 1, n = scaled.size(); i < n; i++) {
            gestureLength += DoublePoint.euclidean(scaled.get(i - 1), scaled.get(i));
        }

        List<DoublePoint> representative = new ArrayList<>(M);
        representative.add(scaled.get(0));

        int current = 1;
        double currentDistance = 0.;
        double interval = gestureLength / (M - 1);
        for (int k = 1; k < M; k++) {
            double targetDistance = k * interval;
            while (currentDistance < targetDistance && current < scaled.size()) {
                currentDistance += DoublePoint.euclidean(scaled.get(current - 1), scaled.get(current));
                current++;
            }

            DoublePoint last = scaled.get(current - 1);
            DoublePoint secondLast = scaled.get(current - 2);
            double leftSide = targetDistance - currentDistance - DoublePoint.euclidean(secondLast, last);
            double rightSide = currentDistance - targetDistance;

            if (current < scaled.size() && rightSide < leftSide) {
                representative.add(scaled.get(current));
            } else {
                representative.add(scaled.get(current - 1));
            }
        }

        double[] output = new double[2 * M];
        for (int i = 0; i < M; i++) {
            DoublePoint point = representative.get(i);
            output[2 * i] = point.x();
            output[2 * i + 1] = point.y();
        }

        return output;
    }
}
