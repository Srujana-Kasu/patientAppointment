package com.application.appointmentBooking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
}
