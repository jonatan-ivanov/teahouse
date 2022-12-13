package org.example.teahouse.tealeaf.repo;

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
import org.example.teahouse.tealeaf.api.CreateTealeafRequest;
import org.example.teahouse.tealeaf.controller.RepresentationTealeafModel;

@Entity
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Tealeaf {
    @Id @GeneratedValue
    private final UUID id;

    @Column(unique = true, nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String type;

    @Column(nullable = false)
    private final String suggestedAmount;

    @Column(nullable = false)
    private final String suggestedWaterTemperature;

    @Column(nullable = false)
    private final String suggestedSteepingTime;

    public RepresentationTealeafModel toRepresentationTealeafModel() {
        return RepresentationTealeafModel.builder()
            .id(this.id)
            .name(this.name)
            .type(this.type)
            .suggestedAmount(this.suggestedAmount)
            .suggestedWaterTemperature(this.suggestedWaterTemperature)
            .suggestedSteepingTime(this.suggestedSteepingTime)
            .build();
    }

    public static Tealeaf fromCreateTealeafRequest(CreateTealeafRequest createTealeafRequest) {
        return Tealeaf.builder()
            .name(createTealeafRequest.getName())
            .type(createTealeafRequest.getType())
            .suggestedAmount(createTealeafRequest.getSuggestedAmount())
            .suggestedWaterTemperature(createTealeafRequest.getSuggestedWaterTemperature())
            .suggestedSteepingTime(createTealeafRequest.getSuggestedSteepingTime())
            .build();
    }
}
