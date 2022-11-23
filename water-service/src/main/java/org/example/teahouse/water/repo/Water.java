package org.example.teahouse.water.repo;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.teahouse.water.api.CreateWaterRequest;
import org.example.teahouse.water.controller.RepresentationWaterModel;

@Entity
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Water {
    @Id @GeneratedValue
    private final UUID id;

    @Column(unique = true, nullable = false)
    private final String size;

    @Column(unique=true, nullable = false)
    private final String amount;

    public RepresentationWaterModel toRepresentationWaterModel() {
        return RepresentationWaterModel.builder()
            .id(this.id)
            .size(this.size)
            .amount(this.amount)
            .build();
    }

    public static Water fromCreateWaterRequest(CreateWaterRequest createWaterRequest) {
        return Water.builder()
            .size(createWaterRequest.getSize())
            .amount(createWaterRequest.getAmount())
            .build();
    }
}
