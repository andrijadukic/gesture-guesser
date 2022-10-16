package neural.learn.backprop;

import neural.sampling.Sample;
import neural.network.FeedForwardNeuralNetwork;

import java.util.List;

public interface Backpropagation {

    void run(FeedForwardNeuralNetwork neuralNetwork, List<Sample> samples);
}
