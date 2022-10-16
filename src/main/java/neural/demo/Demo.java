package neural.demo;

import neural.learn.loss.LossFunction;
import neural.learn.loss.MeanSquareError;
import neural.learn.stopping.StoppingCondition;
import neural.learn.stopping.StoppingConditions;
import neural.network.activations.ActivationFunction;
import neural.network.activations.Activations;
import neural.learn.backprop.OnlineBackpropagation;
import neural.learn.util.NthIterationObserver;
import neural.learn.util.StandardOutputLogger;
import neural.network.FeedForwardNeuralNetwork;
import neural.network.initializers.WeightInitializer;
import neural.network.initializers.WeightInitializers;
import neural.sampling.Sample;

import java.util.Arrays;
import java.util.List;

public class Demo {

    private static final double ETA = 0.1;
    private static final LossFunction MSE = new MeanSquareError();
    private static final StoppingCondition STOP = StoppingConditions.maxIter(1000000).or(StoppingConditions.precision(1e-4));
    private static final WeightInitializer WEIGHT_INITIALIZER = WeightInitializers.gaussian(0, 1);

    private static final int[] NEURONS = new int[]{1, 6, 6, 1};
    private static final ActivationFunction[] ACTIVATIONS = Activations.repeat(Activations.sigmoid(), 3);

    public static void main(String[] args) {
        var samples = List.of(
                new Sample(-1, 1),
                new Sample(-0.8, 0.64),
                new Sample(-0.6, 0.36),
                new Sample(-0.4, 0.16),
                new Sample(-0.2, 0.02),
                new Sample(0.2, 0.02),
                new Sample(0.4, 0.16),
                new Sample(0.6, 0.36),
                new Sample(0.8, 0.64),
                new Sample(1, 1));

        var backpropagation = new OnlineBackpropagation(ETA, MSE, STOP);
        backpropagation.addObserver(new NthIterationObserver(new StandardOutputLogger(), 10000));

        var neural = new FeedForwardNeuralNetwork(NEURONS, ACTIVATIONS, WEIGHT_INITIALIZER, backpropagation).fit(samples);

        samples.forEach(sample -> {
            System.out.println(Arrays.toString(neural.predict(sample.x())));
        });
    }
}
