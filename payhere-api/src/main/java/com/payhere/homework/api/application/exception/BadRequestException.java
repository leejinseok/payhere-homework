package com.payhere.homework.api.application.exception;

import java.util.function.Supplier;

public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message) {
        super(message);
    }

    public static Supplier<BadRequestException> throwBadRequestException(String message) {
        return () -> new BadRequestException(message);
    }
}
