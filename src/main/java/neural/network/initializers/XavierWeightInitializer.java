package neural.network.initializers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class XavierWeightInitializer implements WeightInitializer {

    @Override
    public void initialize(double[][] weights) {
        Random random = ThreadLocalRandom.current();
        for (int i = 0, n = weights.length; i < n; i++) {
            for (int j = 0, m = weights[i].length; j < m; j++) {
                double bound = Math.sqrt(6. / (n + m));
                weights[i][j] = -bound + random.nextDouble() * 2 * bound;
            }
        }
    }
}
