package lt.vu.ads.exceptions;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@CommonsLog
public class ExceptionController {
    private static final String UNKNOWN_SERVER_ERROR_RESPONSE_MESSAGE = "Unknown server error";

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<Object> handleCustomException(NotFoundException ex, WebRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {WrongPasswordException.class})
    public ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException ex, WebRequest request) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleWrongPasswordException(BadRequestException ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {AlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest request) {
        return handleException(ex, HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<Object> handleException(Exception e, HttpStatus status, WebRequest request) {
        String message = "[" + request.toString() + "] " + e.getMessage();
        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            LOG.error(message, e);
        } else {
            LOG.info(message, e);
        }

        Object body = buildResponseBody(e, status);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }

    private Object buildResponseBody(Exception e, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", resolveMessage(status, e));

        return body;
    }

    private String resolveMessage(HttpStatus status, Exception e) {
        // hide original message from user
        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            return UNKNOWN_SERVER_ERROR_RESPONSE_MESSAGE;
        }

        // return validation results
        BindingResult bindingResult = resolveBindingResult(e);
        if (bindingResult != null) {
            return bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        }

        return e.getMessage();
    }

    private BindingResult resolveBindingResult(Exception e) {
        if (e instanceof BindException) {
            return ((BindException) e).getBindingResult();
        }
        return null;
    }
}
