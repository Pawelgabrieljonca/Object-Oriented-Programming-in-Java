    package com.example.rodziny_wyjatkow;

    public class OrderTimeExceededException extends OrderProcessingException{
        public OrderTimeExceededException(String message){
            super(message);
        }
    }
