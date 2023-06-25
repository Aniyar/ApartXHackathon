package com.hackathon.apartxhackathon.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ UserAlreadyExistsException.class})
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_ACCEPTABLE.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({ IncorrectVerificationCodeException.class})
    public final ResponseEntity<ErrorResponse> handleIncorrectVerificationCodeException(IncorrectVerificationCodeException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_ACCEPTABLE.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler({ UserNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ LandLordNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleLandLordNotFoundException(LandLordNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ CityNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleCityNotFoundException(CityNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ApartmentNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleApartmentNotFoundException(ApartmentNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ CleanerNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleCleanerNotFoundException(CleanerNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ OrderNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

}