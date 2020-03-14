package org.example.teahouse.tealeaf.repo;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.teahouse.tealeaf.api.CreateTealeafRequest;

@Entity
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Tealeaf {
    @Id @GeneratedValue
    private final Long id;

    @Column(unique = true, nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String type;

    @Column(nullable = false)
    private final String suggestedAmount;

    @Column(nullable = false)
    private final String suggestedSteepingTime;

    @Column(nullable = false)
    private final String suggestedWaterTemperature;

    public static Tealeaf fromCreateTealeafRequest(CreateTealeafRequest createTealeafRequest) {
        return Tealeaf.builder()
            .name(createTealeafRequest.getName())
            .type(createTealeafRequest.getType())
            .suggestedAmount(createTealeafRequest.getSuggestedAmount())
            .suggestedSteepingTime(createTealeafRequest.getSuggestedSteepingTime())
            .suggestedWaterTemperature(createTealeafRequest.getSuggestedWaterTemperature())
            .build();
    }
}
