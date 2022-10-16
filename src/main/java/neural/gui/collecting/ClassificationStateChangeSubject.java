package neural.gui.collecting;

public interface ClassificationStateChangeSubject {

    void addStateChangeListener(ClassificationStateChangeListener listener);

    void removeStateChangeListener(ClassificationStateChangeListener listener);

    void notifyStateChangeListeners();
}
