package lt.vu.ads.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundExceptionController {

    @ExceptionHandler(value= CustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(CustomException ex)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
