package neural.network.activations;

import java.util.Arrays;

public final class Activations {

    private Activations() {
    }

    public static ActivationFunction identity() {
        return new ActivationFunction() {
            @Override
            public double valueAt(double net) {
                return net;
            }

            @Override
            public double gradientAt(double net) {
                return 1.;
            }
        };
    }

    public static ActivationFunction sigmoid() {
        return new ActivationFunction() {
            @Override
            public double valueAt(double net) {
                return 1. / (1 + Math.exp(-net));
            }

            @Override
            public double gradientAt(double net) {
                return net * (1 - net);
            }
        };
    }

    public static ActivationFunction tanH() {
        return new ActivationFunction() {
            @Override
            public double valueAt(double net) {
                double expOfNet = Math.exp(net);
                double expOfNegNeg = Math.exp(-net);
                return (expOfNet - expOfNegNeg) / (expOfNet + expOfNegNeg);
            }

            @Override
            public double gradientAt(double net) {
                return 1 - Math.pow(valueAt(net), 2);
            }
        };
    }

    public static ActivationFunction reLu() {
        return new ActivationFunction() {
            @Override
            public double valueAt(double net) {
                return Math.max(0, net);
            }

            @Override
            public double gradientAt(double net) {
                if (net == 0) return Double.NaN;
                return net < 0 ? 0 : 1;
            }
        };
    }

    public static ActivationFunction[] repeat(ActivationFunction activation, int n) {
        ActivationFunction[] activations = new ActivationFunction[n];
        Arrays.fill(activations, activation);
        return activations;
    }
}
