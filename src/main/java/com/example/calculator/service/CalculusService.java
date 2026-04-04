package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculusService {

    private double f(double x) {
        return x * x;
    }

    public double derivative(double x) {
        double h = 0.0001;
        return (f(x + h) - f(x)) / h;
    }

    public double integral(double a, double b) {
        int n = 1000;
        double h = (b - a) / n;
        double sum = 0.5 * (f(a) + f(b));

        for (int i = 1; i < n; i++) {
            sum += f(a + i * h);
        }

        return sum * h;
    }
}
