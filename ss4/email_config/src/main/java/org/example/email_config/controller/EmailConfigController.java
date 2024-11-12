package org.example.email_config.controller;

import org.example.email_config.model.EmailConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailConfigController {

    private EmailConfig emailConfig = new EmailConfig();

    @GetMapping("/config")
    public String showConfigForm(Model model) {
        model.addAttribute("emailConfig", emailConfig);
        model.addAttribute("languages", new String[]{"English", "Vietnamese", "Japanese", "Chinese"});
        model.addAttribute("pageSizes", new int[]{5, 10, 15, 25, 50, 100});
        return "emailConfig";
    }

    @PostMapping("/updateConfig")
    public String updateConfig(@ModelAttribute EmailConfig emailConfig, RedirectAttributes redirectAttributes ) {
        this.emailConfig.setLanguage(emailConfig.getLanguage());
        this.emailConfig.setPageSize(emailConfig.getPageSize());
        this.emailConfig.setSpamsFilter(emailConfig.isSpamsFilter()); // cập nhật giá trị spamsFilter
        this.emailConfig.setSignature(emailConfig.getSignature());     // cập nhật chữ ký
        redirectAttributes.addFlashAttribute("message", "Update config successfully");
        return "redirect:/config";
    }
}
