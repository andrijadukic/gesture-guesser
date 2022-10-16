package neural.learn.util;

public final class NthIterationObserver implements MachineLearningModelObserver {

    private final MachineLearningModelObserver observer;
    private final int step;
    private int iterationCount;

    public NthIterationObserver(MachineLearningModelObserver observer, int step) {
        this.observer = observer;
        this.step = step;
    }

    @Override
    public void update(IterationStatistics statistics) {
        if (iterationCount % step == 0) {
            observer.update(statistics);
        }
        iterationCount++;
    }
}
