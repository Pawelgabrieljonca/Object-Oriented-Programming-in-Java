package com.example.rodziny_wyjatkow;

public class Order {
    private String status;      // Status zamówienia (np. "fulfilled", "unfulfilled")
    private String timeStatus;  // Status czasu realizacji zamówienia (np. "on time", "exceeded")

    public Order(String status, String timeStatus) {
        this.status = status;
        this.timeStatus = timeStatus;
    }

    public void checkOrder() throws OrderProcessingException {
        if (!"fulfilled".equalsIgnoreCase(status)) {
            throw new UnfulfilledOrderException("Zamówienie nie zostało zrealizowane!");
        }
        if ("exceeded".equalsIgnoreCase(timeStatus)) {
            throw new OrderTimeExceededException("Przekroczono czas realizacji zamówienia!");
        }
    }
}
