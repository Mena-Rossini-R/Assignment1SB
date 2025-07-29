package com.example.springmvcform.controller;

import com.example.springmvcform.model.Address;
import com.example.springmvcform.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class UserController {

    @GetMapping("/form")
    public String showForm(Model model) {
        User user = new User();
        // Add one empty address object for initial form display
        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(new Address());
        user.setAddresses(addressList);

        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "result";
    }
}
