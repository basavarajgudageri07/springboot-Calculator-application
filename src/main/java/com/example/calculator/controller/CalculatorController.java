package com.example.calculator.controller;

import com.example.calculator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalculatorController {

    @Autowired private CalculatorService calcService;
    @Autowired private CurrencyService currencyService;
    @Autowired private CalculusService calculusService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calc(
            @RequestParam(required = false) Double num1,
            @RequestParam(required = false) Double num2,
            @RequestParam(required = false) String operation,
            Model model) {

        if (num1 == null || num2 == null || operation == null) {
            model.addAttribute("result", "Invalid Input");
            return "index";
        }

        model.addAttribute("result", calcService.calculate(num1, num2, operation));
        return "index";
    }

    @PostMapping("/convert")
    public String convert(double amount, String from, String to, Model model) {
        model.addAttribute("currency", currencyService.convert(amount, from, to));
        return "index";
    }

    @PostMapping("/derivative")
    public String derivative(double x, Model model) {
        model.addAttribute("derivative", calculusService.derivative(x));
        return "index";
    }

    @PostMapping("/integral")
    public String integral(double a, double b, Model model) {
        model.addAttribute("integral", calculusService.integral(a, b));
        return "index";
    }
}
