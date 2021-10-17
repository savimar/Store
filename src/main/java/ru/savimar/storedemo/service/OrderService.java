package ru.savimar.storedemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.savimar.storedemo.entity.Order;
import ru.savimar.storedemo.entity.User;
import ru.savimar.storedemo.repository.OrderRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;


    public List<Order> findAll() {
        List<Order> result = (List<Order>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Order>();
        }
    }


    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Order save(Order order) {
        if (!isEqualOperationId(order)) {
            order.setOperationId(order.hashCode());
            return repository.save(order);
        }
        else {
            return null;
        }
    }
    private boolean isEqualOperationId(Order order) {
        List<Integer> list = repository.findAllOperationId();
        if (list.contains(order.hashCode())
                && (order.getDate().equals(LocalDate.now()) || order.getDate().equals(LocalDate.now().minusDays(1)))) {
            return true;
        } else {
            return false;
        }
    }


    public Order getOrderById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Order> getOrderListByUser(User user) {
        return repository.findAllByUser(user);
    }


}
