package org.example.teahouse.tealeaf.controller;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.tealeaf.api.TealeafModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Builder
@RequiredArgsConstructor
@Relation(collectionRelation = "tealeaves", itemRelation = "tealeaf")
public class RepresentationTealeafModel extends RepresentationModel<RepresentationTealeafModel> implements TealeafModel {
    private final UUID id;
    private final String name;
    private final String type;
    private final String suggestedAmount;
    private final String suggestedWaterTemperature;
    private final String suggestedSteepingTime;
}
