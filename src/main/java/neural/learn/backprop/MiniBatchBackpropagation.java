package neural.learn.backprop;

import neural.learn.loss.LossFunction;
import neural.learn.stopping.StoppingCondition;

public final class MiniBatchBackpropagation extends StochasticBackpropagation {

    private static final int DEFAULT_BATCH_SIZE = 2;

    public MiniBatchBackpropagation() {
        super(DEFAULT_BATCH_SIZE);
    }

    public MiniBatchBackpropagation(double eta, LossFunction lossFunction, StoppingCondition stoppingCondition, int batchSize) {
        super(eta, lossFunction, stoppingCondition, batchSize);
    }
}
