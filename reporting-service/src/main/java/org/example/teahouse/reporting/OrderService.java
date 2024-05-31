package org.example.teahouse.reporting;

import java.util.List;

import io.micrometer.observation.annotation.Observed;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Observed
    public List<Order> getOrdersByTealeaf(String tealeaf) {
        return orderRepository.findByTealeafOrderByTealeafAscTimestampDesc(tealeaf, Pageable.ofSize(100)).getContent();
    }

    @Observed
    public List<Order> getAll() {
        return orderRepository.findAll(PageRequest.of(0, 100, Sort.Direction.DESC, "timestamp")).getContent();
    }

    public List<Order> getOrdersByWater(String water) {
        return orderRepository.findByWater(water);
    }
}
