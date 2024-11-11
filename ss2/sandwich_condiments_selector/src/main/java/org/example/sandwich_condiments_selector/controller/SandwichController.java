package org.example.sandwich_condiments_selector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SandwichController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/save")
    public ModelAndView save(@RequestParam(value = "condiment", required = false) String[] condiments) {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("condiments", condiments != null ? condiments : new String[]{});
        return modelAndView;
    }
}