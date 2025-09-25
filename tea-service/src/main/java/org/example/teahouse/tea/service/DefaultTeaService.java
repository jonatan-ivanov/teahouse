package org.example.teahouse.tea.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.api.Tealeaf;
import org.example.teahouse.tea.api.Water;
import org.example.teahouse.tea.tealeaf.TealeafClient;
import org.example.teahouse.tea.water.WaterClient;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.tealeaf.api.TealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;
import org.example.teahouse.water.api.WaterModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class DefaultTeaService implements TeaService {
    private final WaterClient waterClient;
    private final TealeafClient tealeafClient;

    @Override
    public TeaResponse make(String name, String size) {
        WaterModel waterModel = waterClient.findBySize(size);
        TealeafModel tealeafModel = tealeafClient.findByName(name);

        var build = TeaResponse.builder()
            .water(
                Water.builder()
                    .amount(waterModel.getAmount())
                    .temperature(tealeafModel.getSuggestedWaterTemperature())
                    .build()
            )
            .tealeaf(
                Tealeaf.builder()
                    .name(tealeafModel.getName())
                    .type(tealeafModel.getType())
                    .amount(tealeafModel.getSuggestedAmount())
                    .build()
            )
            .steepingTime(tealeafModel.getSuggestedSteepingTime())
            .build();
        log.atInfo()
            .addKeyValue("teaType", build.tealeaf().type())
            .addKeyValue("teaName", build.tealeaf().name())
            .addKeyValue("waterUsed", build.water().amount())
            .log("Tea served!");
        return build;
    }

    @Override
    public Collection<SimpleTealeafModel> tealeaves() {
        Pageable pageable = PageRequest.ofSize(20); // set to 1 to get one resource per request
        PagedModel<SimpleTealeafModel> tealeavesPage = tealeafClient.tealeaves(pageable);
        Collection<SimpleTealeafModel> tealeaves = new ArrayList<>(tealeavesPage.getContent());

        while (tealeavesPage.getNextLink().isPresent()) {
            pageable = pageable.next();
            tealeavesPage = tealeafClient.tealeaves(pageable);
            tealeaves.addAll(tealeavesPage.getContent());
        }

        return tealeaves;
    }

    @Override
    public Collection<SimpleWaterModel> waters() {
        return waterClient.waters(Pageable.unpaged()).getContent();
    }
}
