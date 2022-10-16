package neural.learn.stopping;

public final class StoppingConditions {

    public static StoppingCondition precision(double epsilon) {
        return statistics -> statistics.error() < epsilon;
    }

    public static StoppingCondition maxIter(int maxIter) {
        return statistics -> statistics.iteration() > maxIter;
    }

    public static StoppingCondition infiniteLoop() {
        return statistics -> false;
    }
}
