package org.example.teahouse.water.repo;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Entity
@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Water {
    @Id @GeneratedValue
    private final long id;

    @Column(unique = true, nullable = false)
    private final String size;

    @Column(unique=true, nullable = false)
    private final String amount;
}
