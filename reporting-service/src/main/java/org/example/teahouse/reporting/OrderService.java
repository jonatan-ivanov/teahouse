package org.example.teahouse.reporting;

import java.util.List;

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

    public List<Order> getOrdersByTealeaf(String tealeaf) {
        return orderRepository.findByTealeafOrderByTimestampDesc(tealeaf, Pageable.ofSize(100)).getContent();
    }

    // TODO: Pagination, currently returns fixed 100 results
    public List<Order> getAll() {
        return orderRepository.findAll(PageRequest.of(0, 100, Sort.Direction.DESC, "timestamp")).getContent();
    }

    public List<Order> getOrdersByWater(String water) {
        return orderRepository.findByWater(water);
    }
}
