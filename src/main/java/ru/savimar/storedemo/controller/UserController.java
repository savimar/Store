package ru.savimar.storedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.savimar.storedemo.entity.User;
import ru.savimar.storedemo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {


    @Autowired
    private UserService service;


    @RequestMapping("/health")
    public @ResponseBody
    String health() {
        return "{\"status\": \"OK\"}";
    }

    @RequestMapping("/")
    public String showUserList(Model model) {
        List<User> userList = service.findAll();
        model.addAttribute("userList", userList);
        return "index";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editUserById(Model model, @PathVariable("id") Optional<Integer> id) {
        if (id.isPresent()) {
            User entity = service.getUserById(id.get());
            model.addAttribute("user", entity);
        } else {
            model.addAttribute("user", new User());
        }

        return "addorder";
    }

    //for postman
    @RequestMapping(path = {"/save", "/save/{id}"})
    public String saveEmployeeById(@RequestBody User user, @PathVariable("id") Optional<Integer> id) {
        if (id.isPresent()) {
            User newUser = service.getUserById(id.get());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            service.save(newUser);
        } else {
            service.save(user);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteUserById(Model model, @PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public String createOrUpdateUser(User user) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);
        User newUser = restTemplate.postForObject("/createBillAndUser", request, User.class);
        service.save(user);
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/";
    }

}
