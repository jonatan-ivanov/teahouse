package org.example.teahouse.water.repo;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.teahouse.water.api.CreateWaterRequest;

@Entity
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Water {
    @Id @GeneratedValue
    private final Long id;

    @Column(unique = true, nullable = false)
    private final String size;

    @Column(unique=true, nullable = false)
    private final String amount;

    public static Water fromCreateWaterRequest(CreateWaterRequest createWaterRequest) {
        return Water.builder()
            .size(createWaterRequest.getSize())
            .amount(createWaterRequest.getAmount())
            .build();
    }
}
