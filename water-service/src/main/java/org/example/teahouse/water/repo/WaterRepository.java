package org.example.teahouse.water.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

//@Cacheable("water")
public interface WaterRepository extends PagingAndSortingRepository<Water, UUID>, CrudRepository<Water, UUID> {
    Optional<Water> findBySize(@Param("size") String size);
}
