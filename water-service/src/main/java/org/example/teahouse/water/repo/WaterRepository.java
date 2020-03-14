package org.example.teahouse.water.repo;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WaterRepository extends CrudRepository<Water, Long> {
    Optional<Water> findBySize(@Param("size") String size);
}
