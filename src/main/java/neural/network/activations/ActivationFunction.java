package neural.network.activations;

public interface ActivationFunction {

    double valueAt(double net);

    double gradientAt(double net);
}
