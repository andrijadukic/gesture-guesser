package neural.learn.stopping;

import neural.learn.util.IterationStatistics;

public interface StoppingCondition {

    boolean isMet(IterationStatistics statistics);

    default StoppingCondition and(StoppingCondition other) {
        return statistics -> isMet(statistics) && other.isMet(statistics);
    }

    default StoppingCondition or(StoppingCondition other) {
        return statistics -> isMet(statistics) || other.isMet(statistics);
    }

    default StoppingCondition not() {
        return statistics -> !isMet(statistics);
    }
}
