package neural.network.initializers;

public interface WeightInitializer {

    void initialize(double[][] weights);

    default void initialize(double[][][] weights) {
        for (double[][] weight : weights) {
            initialize(weight);
        }
    }
}
