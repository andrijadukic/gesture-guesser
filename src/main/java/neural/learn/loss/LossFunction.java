package neural.learn.loss;

import neural.network.MachineLearningModel;
import neural.sampling.Sample;

import java.util.List;

public interface LossFunction {

    double score(MachineLearningModel model, List<Sample> samples);
}
