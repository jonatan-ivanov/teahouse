package org.example.teahouse.tealeaf.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TealeafRepository extends PagingAndSortingRepository<Tealeaf, UUID>, CrudRepository<Tealeaf, UUID> {
    Optional<Tealeaf> findByName(@Param("name") String name);
}
