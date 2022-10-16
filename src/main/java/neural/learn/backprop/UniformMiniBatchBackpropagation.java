package neural.learn.backprop;

import neural.learn.loss.LossFunction;
import neural.sampling.Sample;
import neural.sampling.Sampling;
import neural.learn.stopping.StoppingCondition;

import java.util.List;

public final class UniformMiniBatchBackpropagation extends StochasticBackpropagation {

    public UniformMiniBatchBackpropagation(int batchSize) {
        super(batchSize);
    }

    public UniformMiniBatchBackpropagation(double eta, LossFunction lossFunction, StoppingCondition stoppingCondition, int batchSize) {
        super(eta, lossFunction, stoppingCondition, batchSize);
    }

    @Override
    protected List<List<Sample>> partition(List<Sample> samples) {
        validateBatchSize(samples);

        return Sampling.uniformPartition(samples, batchSize);
    }
}
