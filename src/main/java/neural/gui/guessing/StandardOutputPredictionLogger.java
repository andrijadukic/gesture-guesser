package neural.gui.guessing;

import java.util.Arrays;

public class StandardOutputPredictionLogger implements GestureGuesserListener {

    @Override
    public void onGestureGuesserConclusion(double[] output) {
        System.out.println(Arrays.toString(output));
    }
}
