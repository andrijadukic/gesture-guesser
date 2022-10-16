package neural.network.layers;

import neural.network.initializers.WeightInitializer;

public interface FeedForwardLayer {

    void initialize(WeightInitializer weightInitializer);

    double[] processInput(double[] input);

    double[] processError(double[] error);

    double[] getCachedOutput();

    void update(double[][] weightsUpdate, double[] biasesUpdate);
}
