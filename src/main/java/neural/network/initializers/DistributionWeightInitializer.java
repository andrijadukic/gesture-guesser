package neural.network.initializers;

import java.util.function.DoubleSupplier;

public abstract class DistributionWeightInitializer implements WeightInitializer {

    @Override
    public void initialize(double[][] weights) {
        DoubleSupplier source = getSource();
        for (int i = 0, n = weights.length; i < n; i++) {
            for (int j = 0, m = weights[i].length; j < m; j++) {
                weights[i][j] = source.getAsDouble();
            }
        }
    }

    protected abstract DoubleSupplier getSource();
}
