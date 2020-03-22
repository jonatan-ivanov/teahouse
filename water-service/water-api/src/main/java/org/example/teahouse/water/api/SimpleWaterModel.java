package org.example.teahouse.water.api;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SimpleWaterModel implements WaterModel {
    private final UUID id;
    private final String size;
    private final String amount;
}
