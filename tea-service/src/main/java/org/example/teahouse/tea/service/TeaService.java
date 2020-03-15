package org.example.teahouse.tea.service;

import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.api.Tealeaf;
import org.example.teahouse.tea.api.Water;
import org.example.teahouse.tea.tealeaf.TealeafClient;
import org.example.teahouse.tea.water.WaterClient;
import org.example.teahouse.tealeaf.api.TealeafResponse;
import org.example.teahouse.water.api.WaterResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeaService {
    private final WaterClient waterClient;
    private  final TealeafClient tealeafClient;

    public TeaResponse make(String name, String size) {
        WaterResponse waterResponse = waterClient.findBySize(size);
        TealeafResponse tealeafResponse = tealeafClient.findByName(name);

        return TeaResponse.builder()
            .water(
                Water.builder()
                    .amount(waterResponse.getAmount())
                    .temperature(tealeafResponse.getSuggestedWaterTemperature())
                    .build()
            )
            .tealeaf(
                Tealeaf.builder()
                    .name(tealeafResponse.getName())
                    .type(tealeafResponse.getType())
                    .amount(tealeafResponse.getSuggestedAmount())
                    .build()
            )
            .steepingTime(tealeafResponse.getSuggestedSteepingTime())
            .build();
    }
}
