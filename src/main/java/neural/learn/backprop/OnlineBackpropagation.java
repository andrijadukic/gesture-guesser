package neural.learn.backprop;

import neural.learn.loss.LossFunction;
import neural.learn.stopping.StoppingCondition;

public final class OnlineBackpropagation extends StochasticBackpropagation {

    public OnlineBackpropagation() {
        super(1);
    }

    public OnlineBackpropagation(double eta, LossFunction lossFunction, StoppingCondition stoppingCondition) {
        super(eta, lossFunction, stoppingCondition, 1);
    }
}
