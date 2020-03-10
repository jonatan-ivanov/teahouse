package org.example.teahouse.water.repo;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface WaterRepository extends PagingAndSortingRepository<Water, Long> {
    Optional<Water> findBySize(@Param("size") String size);
}
