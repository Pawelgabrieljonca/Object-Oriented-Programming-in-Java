package com.example.rodziny_wyjatkow;

public class UnfulfilledOrderException extends OrderProcessingException {
    public UnfulfilledOrderException(String message) {
        super(message);
    }
}
