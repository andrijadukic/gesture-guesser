package neural.network.initializers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;

public final class GaussianDistributionWeightInitializer extends DistributionWeightInitializer {

    private final double mean;
    private final double dev;

    public GaussianDistributionWeightInitializer(double mean, double dev) {
        this.mean = mean;
        this.dev = dev;
    }

    @Override
    protected DoubleSupplier getSource() {
        Random random = ThreadLocalRandom.current();
        return () -> dev * random.nextGaussian() + mean;
    }
}
