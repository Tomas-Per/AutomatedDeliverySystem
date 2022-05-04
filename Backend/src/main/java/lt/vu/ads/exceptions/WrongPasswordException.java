package lt.vu.ads.exceptions;

import lombok.Getter;

@Getter
public class WrongPasswordException extends RuntimeException {
    private final String message;

    public WrongPasswordException(String msg) {
        super(msg);
        this.message = msg;
    }

}
