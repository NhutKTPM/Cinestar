package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.UserEntity;
import com.da3.MovieTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin/user")
    public String adminUser(Model model){
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
//        model.addAttribute("newUser", new UserEntity());

        return "admin/admin-user";
    }

//    @PostMapping("/admin/user/addUser")
//    public String addUser (UserEntity newUser){
//        userService.createUser(newUser);
//        return "redirect:/admin/user";
//    }
}
