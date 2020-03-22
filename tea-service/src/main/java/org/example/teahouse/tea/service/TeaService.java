package org.example.teahouse.tea.service;

import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.api.Tealeaf;
import org.example.teahouse.tea.api.Water;
import org.example.teahouse.tea.tealeaf.TealeafClient;
import org.example.teahouse.tea.water.WaterClient;
import org.example.teahouse.tealeaf.api.TealeafModel;
import org.example.teahouse.water.api.WaterModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeaService {
    private final WaterClient waterClient;
    private  final TealeafClient tealeafClient;

    public TeaResponse make(String name, String size) {
        WaterModel waterModel = waterClient.findBySize(size);
        TealeafModel tealeafModel = tealeafClient.findByName(name);

        return TeaResponse.builder()
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
    }
}
