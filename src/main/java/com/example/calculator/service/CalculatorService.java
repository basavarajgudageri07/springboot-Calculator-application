package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double calculate(double a, double b, String op) {

        switch (op) {
            case "add": return a + b;
            case "sub": return a - b;
            case "mul": return a * b;
            case "div": return b != 0 ? a / b : 0;
        }

        return 0;
    }
}
