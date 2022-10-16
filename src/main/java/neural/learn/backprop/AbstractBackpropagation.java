package neural.learn.backprop;

import neural.network.layers.FeedForwardLayer;
import neural.learn.loss.LossFunction;
import neural.learn.loss.MeanSquareError;
import neural.learn.stopping.StoppingCondition;
import neural.learn.stopping.StoppingConditions;
import neural.learn.util.IterationStatistics;
import neural.learn.util.MachineLearningModelObserver;
import neural.learn.util.MachineLearningModelSubject;
import neural.network.FeedForwardNeuralNetwork;
import neural.sampling.Sample;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBackpropagation implements Backpropagation, MachineLearningModelSubject {

    private static final double DEFAULT_ETA = 0.1;
    private static final LossFunction DEFAULT_LOSS_FUNCTION = new MeanSquareError();
    private static final StoppingCondition DEFAULT_STOPPING_CONDITION = StoppingConditions.maxIter(50000);

    private final double eta;
    private final LossFunction lossFunction;
    private final StoppingCondition stoppingCondition;

    private final List<MachineLearningModelObserver> observers;

    public AbstractBackpropagation() {
        this(DEFAULT_ETA, DEFAULT_LOSS_FUNCTION, DEFAULT_STOPPING_CONDITION);
    }

    public AbstractBackpropagation(double eta, LossFunction lossFunction, StoppingCondition stoppingCondition) {
        this.eta = eta;
        this.lossFunction = lossFunction;
        this.stoppingCondition = stoppingCondition;
        observers = new ArrayList<>();
    }

    public double getEta() {
        return eta;
    }

    @Override
    public void addObserver(MachineLearningModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MachineLearningModelObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(IterationStatistics statistics) {
        observers.forEach(observer -> observer.update(statistics));
    }

    @Override
    public final void run(FeedForwardNeuralNetwork neuralNetwork, List<Sample> samples) {
        samples = new ArrayList<>(samples);

        int iter = 0;
        while (true) {
            IterationStatistics statistics = new IterationStatistics(lossFunction.score(neuralNetwork, samples), iter);

            if (stoppingCondition.isMet(statistics)) break;

            notifyObservers(statistics);

            preprocess(samples);
            completeEpoch(neuralNetwork, samples);

            iter++;
        }
    }

    protected void preprocess(List<Sample> samples) {
    }

    private void completeEpoch(FeedForwardNeuralNetwork neuralNetwork, List<Sample> samples) {
        for (List<Sample> batch : partition(samples)) {
            processBatch(neuralNetwork, batch);
        }
    }

    protected abstract List<List<Sample>> partition(List<Sample> samples);

    private void processBatch(FeedForwardNeuralNetwork neuralNetwork, List<Sample> batch) {
        int[] neuronsPerLayer = neuralNetwork.getNeuronsPerLayer();
        List<FeedForwardLayer> layers = neuralNetwork.getLayers();

        double[][][] weightsGradient = FeedForwardNeuralNetwork.constructWeights(neuronsPerLayer);
        double[][] biasGradient = FeedForwardNeuralNetwork.constructBiases(neuronsPerLayer);

        for (Sample sample : batch) {
            double[] input = sample.x();
            double[] expectedOutput = sample.y();
            double[] networkOutput = neuralNetwork.predict(input);
            double[] error = new double[expectedOutput.length];
            for (int i = 0, n = expectedOutput.length; i < n; i++) {
                error[i] = expectedOutput[i] - networkOutput[i];
            }

            for (int k = layers.size() - 1; k >= 0; k--) {
                double[] delta = layers.get(k).processError(error);
                double[] layerOutput = k != 0 ? layers.get(k - 1).getCachedOutput() : input;

                for (int i = 0, n = weightsGradient[k].length; i < n; i++) {
                    for (int j = 0, m = weightsGradient[k][i].length; j < m; j++) {
                        weightsGradient[k][i][j] += delta[j] * layerOutput[i];
                    }
                }
                for (int i = 0, n = biasGradient[k].length; i < n; i++) {
                    biasGradient[k][i] += delta[i];
                }

                error = delta;
            }
        }

        for (int k = 0, l = weightsGradient.length; k < l; k++) {
            for (int i = 0, n = weightsGradient[k].length; i < n; i++) {
                for (int j = 0, m = weightsGradient[k][i].length; j < m; j++) {
                    weightsGradient[k][i][j] *= eta;
                }
            }
        }
        for (int k = 0, l = weightsGradient.length; k < l; k++) {
            for (int i = 0, n = biasGradient[k].length; i < n; i++) {
                biasGradient[k][i] *= eta;
            }
        }

        for (int k = 0, l = layers.size(); k < l; k++) {
            layers.get(k).update(weightsGradient[k], biasGradient[k]);
        }
    }
}
