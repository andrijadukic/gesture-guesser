package neural.gui.guessing;

import neural.gui.shared.SystemConstants;
import neural.learn.backprop.OnlineBackpropagation;
import neural.learn.loss.LossFunction;
import neural.learn.loss.MeanSquareError;
import neural.learn.stopping.StoppingCondition;
import neural.learn.stopping.StoppingConditions;
import neural.learn.util.NthIterationObserver;
import neural.learn.util.StandardOutputLogger;
import neural.network.FeedForwardNeuralNetwork;
import neural.network.activations.ActivationFunction;
import neural.network.activations.Activations;
import neural.network.initializers.WeightInitializer;
import neural.network.initializers.WeightInitializers;
import neural.sampling.Sampling;

import javax.swing.*;
import java.io.IOException;

public class GestureGuesserApp {

    private static final double ETA = 0.1;
    private static final LossFunction MSE = new MeanSquareError();
    private static final StoppingCondition STOP = StoppingConditions.maxIter(30000).or(StoppingConditions.precision(1e-5));
    private static final WeightInitializer WEIGHT_INITIALIZER = WeightInitializers.uniform(-1, 1);

    private static final int[] NEURONS = new int[]{2 * SystemConstants.M, 2 * SystemConstants.M, SystemConstants.CLASS_COUNT_LIMIT};
    private static final ActivationFunction[] ACTIVATIONS = Activations.repeat(Activations.sigmoid(), 2);

    public static void main(String[] args) throws IOException {
        var backpropagation = new OnlineBackpropagation(ETA, MSE, STOP);
        backpropagation.addObserver(new NthIterationObserver(new StandardOutputLogger(), 1000));

        var neural = new FeedForwardNeuralNetwork(NEURONS, ACTIVATIONS, WEIGHT_INITIALIZER, backpropagation);

        neural.fit(Sampling.load(SystemConstants.DATASET_PATH));

        SwingUtilities.invokeLater(() -> new MainGuessingFrame(neural).setVisible(true));
    }
}
