package neural.network.initializers;

public final class WeightInitializers {

    private WeightInitializers() {
    }

    public static WeightInitializer uniform(double lb, double ub) {
        return new UniformDistributionWeightInitializer(lb, ub);
    }

    public static WeightInitializer gaussian(double mean, double dev) {
        return new GaussianDistributionWeightInitializer(mean, dev);
    }

    public static WeightInitializer xavier() {
        return new XavierWeightInitializer();
    }
}
