package org.example.teahouse.reporting;

import java.util.List;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public List<Order> getOrdersByTealeaf(@RequestParam("tealeaf") String tealeaf) {
        log.info("Finding by tealeaf <{}>", tealeaf);
        return orderRepository.findByTealeafOrderByTealeafAscTimestampDesc(tealeaf, Pageable.ofSize(100)).getContent();
    }
}
