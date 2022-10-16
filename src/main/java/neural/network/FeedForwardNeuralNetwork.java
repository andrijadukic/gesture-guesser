package neural.network;

import neural.exceptions.ModelNotFittedException;
import neural.network.activations.ActivationFunction;
import neural.network.layers.FeedForwardLayer;
import neural.network.layers.HiddenFeedForwardLayer;
import neural.network.layers.OutputFeedForwardLayer;
import neural.learn.backprop.Backpropagation;
import neural.learn.backprop.OnlineBackpropagation;
import neural.network.initializers.WeightInitializer;
import neural.network.initializers.WeightInitializers;
import neural.sampling.Sample;

import java.util.List;

public final class FeedForwardNeuralNetwork implements MachineLearningModel {

    private static final WeightInitializer DEFAULT_WEIGHT_INITIALIZER = WeightInitializers.gaussian(0, 1);
    private static final Backpropagation DEFAULT_BACKPROPAGATION_METHOD = new OnlineBackpropagation();

    private final int[] neuronsPerLayer;
    private final FeedForwardLayer[] layers;
    private final List<FeedForwardLayer> roLayers;

    private final WeightInitializer weightInitializer;

    private final Backpropagation backpropagationMethod;

    private boolean isFitted;

    public FeedForwardNeuralNetwork(int[] neuronsPerLayer, ActivationFunction[] activationFunctionsPerLayer) {
        this(neuronsPerLayer, activationFunctionsPerLayer, DEFAULT_BACKPROPAGATION_METHOD);
    }

    public FeedForwardNeuralNetwork(int[] neuronsPerLayer, ActivationFunction[] activationFunctionsPerLayer, Backpropagation backpropagationMethod) {
        this(neuronsPerLayer, activationFunctionsPerLayer, DEFAULT_WEIGHT_INITIALIZER, backpropagationMethod);
    }

    public FeedForwardNeuralNetwork(int[] neuronsPerLayer,
                                    ActivationFunction[] activationFunctionsPerLayer,
                                    WeightInitializer weightInitializer,
                                    Backpropagation backpropagationMethod) {
        this.neuronsPerLayer = neuronsPerLayer;
        layers = constructLayers(neuronsPerLayer, activationFunctionsPerLayer);
        roLayers = List.of(layers);
        this.weightInitializer = weightInitializer;
        this.backpropagationMethod = backpropagationMethod;
    }

    public int[] getNeuronsPerLayer() {
        return neuronsPerLayer;
    }

    public List<FeedForwardLayer> getLayers() {
        return roLayers;
    }

    @Override
    public FeedForwardNeuralNetwork fit(List<Sample> samples) {
        getLayers().forEach(layer -> layer.initialize(weightInitializer));
        isFitted = true;
        backpropagationMethod.run(this, samples);
        return this;
    }

    @Override
    public FeedForwardNeuralNetwork partialFit(List<Sample> samples) {
        if (!isFitted) {
            getLayers().forEach(layer -> layer.initialize(weightInitializer));
            isFitted = true;
        }
        backpropagationMethod.run(this, samples);
        return this;
    }

    @Override
    public double[] predict(double[] x) {
        if (!isFitted) throw new ModelNotFittedException(FeedForwardNeuralNetwork.class);

        double[] output = layers[0].processInput(x);
        for (int i = 1, n = layers.length; i < n; i++) {
            output = layers[i].processInput(output);
        }
        return output;
    }

    public static FeedForwardLayer[] constructLayers(int[] neuronsPerLayer, ActivationFunction[] activationFunctionsPerLayer) {
        double[][][] weights = constructWeights(neuronsPerLayer);
        double[][] biases = constructBiases(neuronsPerLayer);

        int layersCount = neuronsPerLayer.length - 1;
        int hiddenLayersCount = layersCount - 1;
        FeedForwardLayer[] layers = new FeedForwardLayer[layersCount];
        for (int i = 0; i < hiddenLayersCount; i++) {
            layers[i] = new HiddenFeedForwardLayer(weights[i], biases[i], activationFunctionsPerLayer[i], weights[i + 1]);
        }
        layers[hiddenLayersCount] = new OutputFeedForwardLayer(weights[hiddenLayersCount], biases[hiddenLayersCount], activationFunctionsPerLayer[hiddenLayersCount]);

        return layers;
    }

    public static double[][][] constructWeights(int[] neuronsPerLayer) {
        int layersCount = neuronsPerLayer.length - 1;
        double[][][] weights = new double[layersCount][][];
        for (int i = 0; i < layersCount; i++) {
            weights[i] = new double[neuronsPerLayer[i]][neuronsPerLayer[i + 1]];
        }
        return weights;
    }

    public static double[][] constructBiases(int[] neuronsPerLayer) {
        int layersCount = neuronsPerLayer.length - 1;
        double[][] biases = new double[layersCount][];
        for (int i = 0; i < biases.length; i++) {
            biases[i] = new double[neuronsPerLayer[i + 1]];
        }
        return biases;
    }
}
