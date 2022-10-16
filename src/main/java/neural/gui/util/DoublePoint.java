package neural.gui.util;

public record DoublePoint(double x, double y) {

    public static double euclidean(DoublePoint a, DoublePoint b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
