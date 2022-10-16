# gesture-guesser

gesture-guesser is a Java project for collecting samples of handwritten gestures and then using the collected samples to
train a feedforward neural network, built into a UI where you can test the behaviour of the neural network on new
samples given by the user.

## gesture-collector

By running the [GestureCollectorApp](src/main/java/neural/gui/collecting/GestureCollectorApp.java), through the given
UI, users can collect and classify arbitrary handwritten symbols, persisting the samples onto disk when finished.

## gesture-guesser

By running the [GestureCollectorApp](src/main/java/neural/gui/collecting/GestureGuesserApp.java), users first train and
configure the neural network, then, through the given UI can provide the neural network with handwritten gestures,
classifying the samples as they go.