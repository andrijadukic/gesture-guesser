package neural.network.layers;

import neural.network.activations.ActivationFunction;

public final class OutputFeedForwardLayer extends AbstractFeedForwardLayer {

    public OutputFeedForwardLayer(double[][] weights, double[] biases, ActivationFunction activationFunction) {
        super(weights, biases, activationFunction);
    }

    @Override
    public double[] processError(double[] error) {
        double[] layerError = new double[neuronCount];
        double[] cachedOutput = getCachedOutput();
        for (int i = 0; i < neuronCount; i++) {
            layerError[i] = activationFunction.gradientAt(cachedOutput[i]) * error[i];
        }
        return layerError;
    }
}
