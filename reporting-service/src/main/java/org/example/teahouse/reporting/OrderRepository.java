package org.example.teahouse.reporting;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findByTealeafOrderByTimestampDesc(String tealeaf, Pageable pageable);

    List<Order> findByWater(String tealeaf);
}
