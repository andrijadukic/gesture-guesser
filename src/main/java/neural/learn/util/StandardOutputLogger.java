package neural.learn.util;

public final class StandardOutputLogger implements MachineLearningModelObserver {

    @Override
    public void update(IterationStatistics statistics) {
        System.out.println("Iteration: " + statistics.iteration() + " | Error: " + statistics.error());
    }
}
