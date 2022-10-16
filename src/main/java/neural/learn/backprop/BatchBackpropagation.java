package neural.learn.backprop;

import neural.learn.loss.LossFunction;
import neural.sampling.Sample;
import neural.sampling.Sampling;
import neural.learn.stopping.StoppingCondition;

import java.util.List;

public final class BatchBackpropagation extends AbstractBackpropagation {

    public BatchBackpropagation() {
    }

    public BatchBackpropagation(double eta, LossFunction lossFunction, StoppingCondition stoppingCondition) {
        super(eta, lossFunction, stoppingCondition);
    }

    @Override
    protected List<List<Sample>> partition(List<Sample> samples) {
        return Sampling.partition(samples, samples.size());
    }
}
