package lt.vu.ads.exceptions;

import javax.persistence.OptimisticLockException;

public class CustomOptimisticLockException extends RuntimeException {
    public CustomOptimisticLockException(String msg) {
        super(msg, new OptimisticLockException("Older version of entity found"));
    }
}