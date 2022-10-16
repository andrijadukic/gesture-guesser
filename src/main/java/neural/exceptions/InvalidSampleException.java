package neural.exceptions;

public class InvalidSampleException extends RuntimeException {

    public InvalidSampleException() {
        super("Provided sample is not valid");
    }

    public InvalidSampleException(String message) {
        super("Provided sample is not valid. " + message);
    }
}
