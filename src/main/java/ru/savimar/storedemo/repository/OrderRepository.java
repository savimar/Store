package ru.savimar.storedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.savimar.storedemo.entity.Order;
import ru.savimar.storedemo.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
    @Query("SELECT order.operationId  FROM Order order ORDER BY order.operationId ASC")
    List<Integer> findAllOperationId();
}
