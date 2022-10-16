package neural.network;

import neural.sampling.Sample;

import java.util.List;

public interface MachineLearningModel {

    MachineLearningModel fit(List<Sample> samples);

    MachineLearningModel partialFit(List<Sample> samples);

    double[] predict(double[] x);

    default double[][] predict(double[]... data) {
        int n = data.length;
        double[][] predictions = new double[n][];
        for (int i = 0; i < n; i++) {
            predictions[i] = predict(data[i]);
        }
        return predictions;
    }
}
