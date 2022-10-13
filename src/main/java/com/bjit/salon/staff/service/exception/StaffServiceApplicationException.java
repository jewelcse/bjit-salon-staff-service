package com.bjit.salon.staff.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class StaffServiceApplicationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<List<String>> processUnmergeException(final MethodArgumentNotValidException ex) {

        List<String> list = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = StaffNotFoundException.class)
    public ResponseEntity<ExceptionResponse> salonNotFoundException(StaffNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage()));
    }




    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ExceptionResponse{
        private String message;
    }
}
