package com.payhere.homework.api.application.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }

    public static Supplier<NotFoundException> throwNotFoundException(String message) {
        return () -> new NotFoundException(message);
    }

}
