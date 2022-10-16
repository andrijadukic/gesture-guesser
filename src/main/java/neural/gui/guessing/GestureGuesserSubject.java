package neural.gui.guessing;

public interface GestureGuesserSubject {

    void addGestureGuesserListener(GestureGuesserListener listener);

    void removeGestureGuesserListener(GestureGuesserListener listener);

    void notifyGestureGuesserListeners(double[] output);
}
