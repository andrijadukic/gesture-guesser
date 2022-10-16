package neural.network.layers;

import neural.network.activations.ActivationFunction;

public final class HiddenFeedForwardLayer extends AbstractFeedForwardLayer {

    private final int nextLayerNeuronCount;
    private final double[][] nextLayerWeights;

    public HiddenFeedForwardLayer(double[][] weights, double[] biases, ActivationFunction activationFunction, double[][] nextLayerWeights) {
        super(weights, biases, activationFunction);
        this.nextLayerNeuronCount = nextLayerWeights[0].length;
        this.nextLayerWeights = nextLayerWeights;
    }

    @Override
    public double[] processError(double[] error) {
        double[] layerError = new double[neuronCount];
        double[] cachedOutput = getCachedOutput();
        for (int i = 0; i < neuronCount; i++) {
            double sum = 0.;
            for (int j = 0; j < nextLayerNeuronCount; j++) {
                sum += error[j] * nextLayerWeights[i][j];
            }
            layerError[i] = activationFunction.gradientAt(cachedOutput[i]) * sum;
        }
        return layerError;
    }
}
