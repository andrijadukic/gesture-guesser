package neural.network.initializers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;

public final class UniformDistributionWeightInitializer extends DistributionWeightInitializer {

    private final double lb;
    private final double ub;

    public UniformDistributionWeightInitializer(double lb, double ub) {
        this.lb = lb;
        this.ub = ub;
    }

    @Override
    protected DoubleSupplier getSource() {
        Random random = ThreadLocalRandom.current();
        return () -> lb + random.nextDouble() * (ub - lb);
    }
}
