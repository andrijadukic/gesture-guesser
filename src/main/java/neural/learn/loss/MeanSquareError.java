package neural.learn.loss;

import neural.network.MachineLearningModel;
import neural.sampling.Sample;

import java.util.List;

public class MeanSquareError implements LossFunction {

    @Override
    public double score(MachineLearningModel model, List<Sample> samples) {
        double mse = 0.;
        for (Sample sample : samples) {
            double[] target = sample.y();
            double[] output = model.predict(sample.x());
            for (int i = 0, n = output.length; i < n; i++) {
                mse += Math.pow(target[i] - output[i], 2);
            }
        }
        return mse / (2 * samples.size());
    }
}
