package neural.learn.backprop;

import neural.exceptions.InsufficientSampleSizeException;
import neural.learn.loss.LossFunction;
import neural.sampling.Sample;
import neural.learn.stopping.StoppingCondition;
import neural.sampling.Sampling;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class StochasticBackpropagation extends AbstractBackpropagation {

    protected final int batchSize;

    public StochasticBackpropagation(int batchSize) {
        this.batchSize = batchSize;
    }

    public StochasticBackpropagation(double eta, LossFunction lossFunction, StoppingCondition stoppingCondition, int batchSize) {
        super(eta, lossFunction, stoppingCondition);
        this.batchSize = batchSize;
    }

    @Override
    protected void preprocess(List<Sample> samples) {
        Collections.shuffle(samples, ThreadLocalRandom.current());
    }

    @Override
    protected List<List<Sample>> partition(List<Sample> samples) {
        validateBatchSize(samples);
        return Sampling.partition(samples, batchSize);
    }

    protected void validateBatchSize(List<Sample> samples) {
        if (samples.size() < batchSize) throw new InsufficientSampleSizeException(batchSize, samples.size());
    }
}
