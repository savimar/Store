package ru.savimar.storedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.savimar.storedemo.entity.Letter;
import ru.savimar.storedemo.entity.Order;
import ru.savimar.storedemo.entity.OrderStatus;
import ru.savimar.storedemo.entity.User;
import ru.savimar.storedemo.service.OrderService;
import ru.savimar.storedemo.service.ProducerService;
import ru.savimar.storedemo.service.UserService;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ProducerService producerService;

    @RequestMapping("/orders")
    public String showOrders(Model model) {
        List<Order> orderList = service.findAll();
        model.addAttribute("orderList", orderList);
        return "orders";
    }
    @RequestMapping(path = "/orderForUser/{id}")
    public String getOrdersForUser(Model model, @PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        List<Order> orderList = service.getOrderListByUser(user);
        model.addAttribute("orderList", orderList);
        RestTemplate restTemplate = new RestTemplate();

       // HttpEntity<User> request = new HttpEntity<>(user);
     //   Integer account = restTemplate.postForObject("/getBillForUser", request, Integer.class);
        return "orders";
    }
    @RequestMapping(path = "/createOrderForUser/{id}")
    public String createOrdersForUser(@PathVariable("id") Integer id, @RequestParam Order order) {
        User user = userService.getUserById(id);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);
        Integer account = restTemplate.postForObject("/getBillForUser", request, Integer.class);
        Letter letter = new Letter();
        letter.setTo(user.getEmail());
        String message= "";
        if (account < order.getPrice()){
           message= "bad";
           letter.setMessage(message);
           producerService.produce(letter);
           return "Bad";
        }
        else{
           message = "good";
           letter.setMessage(message);
           producerService.produce(letter);
           return "OK";
        }
    }
    @RequestMapping(path = "/editBill/{id}")
    public String etitBill(@PathVariable("id") Integer id, @RequestParam Integer account) {
        User user = userService.getUserById(id);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForLocation("/editBillForUser", request, account);
        return "OK";
    }
    @RequestMapping(path = {"/editOrder", "/editOrder/{id}"})
    public String editUserById(Model model, @PathVariable("id") Optional<Integer> id) {

        if (id.isPresent()) {
            Order entity = service.getOrderById(id.get());
            model.addAttribute("order", entity);
        } else {
            model.addAttribute("order", new Order());
        }

        return "addorder";
    }

    //for postman
    @RequestMapping(path = {"/saveOrder", "/saveOrder/{id}"})
    public String saveOrderById(Model model, @RequestBody Order order, @PathVariable("id") Optional<Integer> id) {
        Order saveOrder;
        if (id.isPresent()) {
            Order newOrder = service.getOrderById(id.get());
            newOrder.setPrice(order.getPrice());
            newOrder.setProduct(order.getProduct());
            newOrder.setOrderStatus(OrderStatus.PENDING);
            saveOrder = service.save(newOrder);
            } else {
            saveOrder = service.save(order);
        }
        if(saveOrder != null) {
            List<Order> orderList = service.getOrderListByUser(userService.getUserById(1));
            model.addAttribute("orderList", orderList);
            return "orders";
        }else {
            return "sameorder";
        }
    }

    @RequestMapping(path = "/deleteOrder/{id}")
    public String deleteOrderById(Model model, @PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createOrder/{userId}", method = RequestMethod.POST)
    public String createOrUpdateOrder(Model model, Order order,  @PathVariable("userId") Optional<Integer> userId) {
        Order saveOrder;
        order.setCompleted(false);
        order.setUser(userService.getUserById(userId.get()));
        order.setDate(LocalDate.now());
        saveOrder = service.save(order);
        if(saveOrder != null) {
            List<Order> orderList = service.getOrderListByUser(userService.getUserById(1));
            model.addAttribute("orderList", orderList);
            return "orders";
        }else {
            return "sameorder";
        }
    }


}
