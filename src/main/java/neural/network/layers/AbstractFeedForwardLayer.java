package neural.network.layers;

import neural.exceptions.InputDimensionMismatch;
import neural.network.activations.ActivationFunction;
import neural.exceptions.InputNotProcessedException;
import neural.network.initializers.WeightInitializer;

public abstract class AbstractFeedForwardLayer implements FeedForwardLayer {

    protected final double[][] weights;
    protected final double[] biases;
    protected final ActivationFunction activationFunction;

    protected final int previousLayerNeuronCount;
    protected final int neuronCount;

    private double[] cachedOutput;

    public AbstractFeedForwardLayer(double[][] weights, double[] biases, ActivationFunction activationFunction) {
        this.weights = weights;
        this.biases = biases;
        this.activationFunction = activationFunction;
        previousLayerNeuronCount = weights.length;
        neuronCount = biases.length;
    }

    @Override
    public void initialize(WeightInitializer weightInitializer) {
        weightInitializer.initialize(weights);
        for (int i = 0; i < neuronCount; i++) {
            biases[i] = 0;
        }
    }

    @Override
    public double[] processInput(double[] input) {
        if (input.length != previousLayerNeuronCount)
            throw new InputDimensionMismatch(previousLayerNeuronCount, input.length);

        double[] output = new double[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            output[i] = activationFunction.valueAt(net(i, input));
        }
        cachedOutput = output;
        return output;
    }

    private double net(int i, double[] input) {
        double net = biases[i];
        for (int j = 0; j < previousLayerNeuronCount; j++) {
            net += input[j] * weights[j][i];
        }
        return net;
    }

    @Override
    public void update(double[][] weightsUpdate, double[] biasesUpdate) {
        for (int i = 0; i < previousLayerNeuronCount; i++) {
            for (int j = 0; j < neuronCount; j++) {
                weights[i][j] += weightsUpdate[i][j];
                biases[j] += biasesUpdate[j];
            }
        }
        cachedOutput = null;
    }

    @Override
    public double[] getCachedOutput() {
        if (cachedOutput == null) throw new InputNotProcessedException();
        return cachedOutput;
    }
}
