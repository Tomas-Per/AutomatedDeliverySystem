package lt.vu.ads.exceptions;

public class AlreadyExistsException extends NotFoundException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}