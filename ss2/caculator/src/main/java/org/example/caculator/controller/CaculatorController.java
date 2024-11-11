package org.example.caculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CaculatorController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/caculate")
    public String caculate(@RequestParam String num1, @RequestParam String num2, @RequestParam String operator, Model model) {
        double result;
        try {
            double number1 = Double.parseDouble(num1);
            double number2 = Double.parseDouble(num2);
            switch (operator) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                case "/":
                    if (number2 != 0) {
                        result = number1 / number2;
                    } else {
                        model.addAttribute("error", "Cannot divide by zero");
                        return "index";
                    }
                    break;
                case "%":
                    if (number2 != 0) {
                        result = (number1 / number2) * 100;
                    } else {
                        model.addAttribute("error", "Cannot divide by zero");
                        return "index";
                    }
                    break;
                case "^":
                    result = Math.pow(number1, number2);
                    break;
                default:
                    model.addAttribute("error", "Invalid operation");
                    return "index";
            }
            model.addAttribute("result", result);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid number format");
        }
        return "index";
    }
}