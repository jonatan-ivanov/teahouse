package org.example.teahouse.tealeaf.repo;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TealeafRepository extends CrudRepository<Tealeaf, Long> {
    Optional<Tealeaf> findByName(@Param("name") String name);
}
