package com.whatsapp.api.error.handler;

import com.whatsapp.api.controller.view.GenericMessageView;
import com.whatsapp.api.error.RecordNotFoundException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Validated
@Slf4j
public class CustomExceptionHandler {
    private static final String LOG_MESSAGE = "Error message: {}. Details:";

    @ExceptionHandler({ RecordNotFoundException.class })
    public final ResponseEntity<GenericMessageView> handleNotFound(Exception ex) {
        log.warn(LOG_MESSAGE, ex.getMessage(), ex.fillInStackTrace());

        return new ResponseEntity(new GenericMessageView(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class })
    public final ResponseEntity<GenericMessageView> handleBadRequest(Exception ex) {
        log.warn(LOG_MESSAGE, ex.getMessage(), ex.fillInStackTrace());

        return new ResponseEntity(new GenericMessageView(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<GenericMessageView> handleGenericException(Exception ex) {
        log.error(LOG_MESSAGE, ex.getMessage(), ex.fillInStackTrace());

        return new ResponseEntity(new GenericMessageView("Encountered internal error."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
